/**
 * Copyright Tong S.A.S., 2003-2023. All Rights Reserved.
 * <p>
 * This software program is proprietary and confidential to Tong S.A.S and its affiliates ("Tong") and, without limiting the generality of the foregoing reservation of rights, shall not be accessed, used, reproduced or distributed without the
 * express prior written consent of Tong and subject to the applicable Tong licensing terms. Any modification or removal of this copyright notice is expressly prohibited.
 */
package main.snapshot;

import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.diff.compare.DatabaseObjectComparatorFactory;
import liquibase.exception.DatabaseException;
import liquibase.snapshot.CachedRow;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.JdbcDatabaseSnapshot;
import liquibase.snapshot.jvm.IndexSnapshotGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyIndexSnapshotGenerator extends IndexSnapshotGenerator {

    private Relation exampleIndex;
    private String tableName;
    private Schema schema;
    private String exampleName;
    private Database database;
    private final Map<String, Index> foundIndexes = new HashMap<>();

    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        return PRIORITY_DATABASE;
    }

    /**
     * Override snapshotObject() method for fixing exception in parent class for MSSQLDatabase.
     * <p>
     * After getting "IS_INCLUDED_COLUMN" from row: row.get("IS_INCLUDED_COLUMN"), the parent method cast directly the return value to Boolean,
     * which will throw java.lang.NullPointerException when the value is null (Cause the property has a lower case name "is_included_column" for MSSQLDatabase).
     * <p>
     * In this context: getting value by lower case name and add non-null validation for fixing
     */
    @Override
    protected DatabaseObject snapshotObject(DatabaseObject example, DatabaseSnapshot snapshot) throws DatabaseException, InvalidExampleException {
        database = snapshot.getDatabase();
        if (!(database instanceof MSSQLDatabase)) {
            return super.snapshotObject(example, snapshot);
        }
        parseDatabaseObject(example);
        try {
            JdbcDatabaseSnapshot.CachingDatabaseMetaData databaseMetaData = ((JdbcDatabaseSnapshot) snapshot).getMetaDataFromCache();
            List<CachedRow> resultSet = databaseMetaData.getIndexInfo(((AbstractJdbcDatabase) database).getJdbcCatalogName(schema),
                    ((AbstractJdbcDatabase) database).getJdbcSchemaName(schema), tableName, exampleName);
            for (CachedRow row : resultSet) {
                String rawIndexName = row.getString("INDEX_NAME");
                String indexName = this.cleanNameFromDatabase(rawIndexName, database);
                String correctedIndexName = database.correctObjectName(indexName, Index.class);
                Index returnIndex = foundIndexes.get(correctedIndexName);

                Object includedColumns = new Table();
                if ("V".equals(row.getString("INTERNAL_OBJECT_TYPE"))) {
                    includedColumns = new View();
                }
                if (returnIndex == null) {
                    returnIndex = generateReturnIndex(row, indexName, includedColumns);
                    foundIndexes.put(correctedIndexName, returnIndex);
                }
                enrichReturnIndex(row, returnIndex, includedColumns);
            }
        } catch (Exception var25) {
            throw new DatabaseException(var25);
        }
        return generateIndexSnapshot(example, snapshot, exampleIndex);
    }

    private void parseDatabaseObject(DatabaseObject example) {
        exampleIndex = ((Index) example).getRelation();
        if (exampleIndex != null) {
            tableName = exampleIndex.getName();
            schema = exampleIndex.getSchema();
        }
        if (schema == null) {
            schema = new Schema(database.getDefaultCatalogName(), database.getDefaultSchemaName());
        }

        for (int i = 0; i < ((Index) example).getColumns().size(); ++i) {
            ((Index) example).getColumns().set(i, ((Index) example).getColumns().get(i));
        }
        exampleName = example.getName();
        if (exampleName != null) {
            exampleName = database.correctObjectName(exampleName, Index.class);
        }
    }

    private Index generateReturnIndex(CachedRow row, String indexName, Object includedColumns) {
        Index returnIndex = new Index();
        Relation relation = ((Relation) includedColumns).setName(tableName).setSchema(schema);
        returnIndex.setRelation(relation);
        returnIndex.setName(indexName);

        Boolean nonUnique = row.getBoolean("NON_UNIQUE");
        if (nonUnique == null) {
            nonUnique = true;
        }
        returnIndex.setUnique(!nonUnique);
        String tablespaceName = row.getString("TABLESPACE_NAME");
        if (tablespaceName != null && database.supportsTablespaces()) {
            returnIndex.setTablespace(tablespaceName);
        }

        short type = row.getShort("TYPE");
        returnIndex.setClustered(type == 1);

        String noRecompute = "NO_RECOMPUTE";
        if (!row.containsColumn(noRecompute)) {
            noRecompute = "no_recompute";
        }
        Boolean recompute = (Boolean) row.get(noRecompute);
        if (recompute != null) {
            recompute = !recompute;
        }
        returnIndex.setAttribute("padIndex", row.get("IS_PADDED"));
        returnIndex.setAttribute("fillFactor", row.get("FILL_FACTOR"));
        returnIndex.setAttribute("ignoreDuplicateKeys", row.get("IGNORE_DUP_KEY"));
        returnIndex.setAttribute("recomputeStatistics", recompute);
        returnIndex.setAttribute("incrementalStatistics", row.get("IS_INCREMENTAL"));
        returnIndex.setAttribute("allowRowLocks", row.get("ALLOW_ROW_LOCKS"));
        returnIndex.setAttribute("allowPageLocks", row.get("ALLOW_PAGE_LOCKS"));
        return returnIndex;
    }

    private void enrichReturnIndex(CachedRow row, Index returnIndex, Object includedColumns) {
        String columnName = this.cleanNameFromDatabase(row.getString("COLUMN_NAME"), database);
        short position = row.getShort("ORDINAL_POSITION");

        Object includedColumn = (row.containsColumn("IS_INCLUDED_COLUMN")) ?
                row.containsColumn("IS_INCLUDED_COLUMN") : row.get("is_included_column");

        if (includedColumn != null && (Boolean) includedColumn) {
            if (returnIndex.getAttribute("includedColumns", List.class) == null) {
                includedColumns = new ArrayList();
                returnIndex.setAttribute("includedColumns", includedColumns);
            }
            ((List) includedColumns).add(columnName);
        } else if (position != 0) {
            for (int i = returnIndex.getColumns().size(); i < position; ++i) {
                returnIndex.getColumns().add(null);
            }

            String ascOrDesc = row.getString("ASC_OR_DESC");
            Boolean ascending = "A".equals(ascOrDesc) ? Boolean.FALSE : null;
            Boolean descending = "D".equals(ascOrDesc) ? Boolean.TRUE : ascending;
            returnIndex.getColumns().set(position - 1, (new Column(columnName)).setDescending(descending).setRelation(returnIndex.getRelation()));
        }
    }

    private Index generateIndexSnapshot(DatabaseObject example, DatabaseSnapshot snapshot, Relation exampleIndex) {
        if (exampleName != null) {
            return foundIndexes.get(exampleName);
        }

        List<Index> nonClusteredIndexes = new ArrayList<>();
        for (Index index : foundIndexes.values()) {
            boolean isSameObject = DatabaseObjectComparatorFactory.getInstance().isSameObject(index.getRelation(), exampleIndex, snapshot.getSchemaComparisons(), database);
            boolean isActuallyMatches = isActuallyMatches(database, index, example);
            if (isSameObject && isActuallyMatches) {
                if (index.getClustered() != null && index.getClustered()) {
                    return this.finalizeIndex(schema, tableName, index, snapshot);
                }
                nonClusteredIndexes.add(index);
            }
        }

        if (!nonClusteredIndexes.isEmpty()) {
            return this.finalizeIndex(schema, tableName, nonClusteredIndexes.get(0), snapshot);
        }
        return null;
    }

    private Boolean isActuallyMatches(Database database, Index index, DatabaseObject example) {
        boolean actuallyMatches = false;
        if (database.isCaseSensitive()) {
            if (index.getColumnNames().equals(((Index) example).getColumnNames())) {
                actuallyMatches = true;
            }
        } else if (index.getColumnNames().equalsIgnoreCase(((Index) example).getColumnNames())) {
            actuallyMatches = true;
        }
        return actuallyMatches;
    }
}
