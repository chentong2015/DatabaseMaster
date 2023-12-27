package main.snapshot;

import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.Database;
import liquibase.database.core.*;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.compare.DatabaseObjectComparatorFactory;
import liquibase.exception.DatabaseException;
import liquibase.snapshot.CachedRow;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.JdbcDatabaseSnapshot;
import liquibase.snapshot.jvm.IndexSnapshotGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.*;
import liquibase.util.StringUtil;

import java.util.*;

public class FilteringIndexSnapshotGeneratorTemp extends IndexSnapshotGenerator {

    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        return PRIORITY_DATABASE;
    }

    @Override
    protected void addTo(DatabaseObject foundObject, DatabaseSnapshot snapshot) throws DatabaseException, InvalidExampleException {
        if (snapshot.getSnapshotControl().shouldInclude(Index.class)) {
            if (foundObject instanceof Table || foundObject instanceof View) {
                if (foundObject instanceof View && !this.addToViews(snapshot.getDatabase())) {
                    return;
                }

                Relation relation = (Relation) foundObject;
                Database database = snapshot.getDatabase();
                Schema schema = relation.getSchema();
                List<CachedRow> rs = null;
                JdbcDatabaseSnapshot.CachingDatabaseMetaData databaseMetaData = null;

                try {
                    databaseMetaData = ((JdbcDatabaseSnapshot) snapshot).getMetaDataFromCache();
                    rs = databaseMetaData.getIndexInfo(((AbstractJdbcDatabase) database).getJdbcCatalogName(schema), ((AbstractJdbcDatabase) database).getJdbcSchemaName(schema), relation.getName(), (String) null);
                    Map<String, Index> foundIndexes = new HashMap();
                    Iterator var9 = rs.iterator();

                    label122:
                    while (true) {
                        CachedRow row;
                        String indexName;
                        do {
                            do {
                                if (!var9.hasNext()) {
                                    List<Index> stillToAdd = new ArrayList();
                                    Iterator var18 = foundIndexes.values().iterator();

                                    while (true) {
                                        Index exampleIndex;
                                        while (var18.hasNext()) {
                                            exampleIndex = (Index) var18.next();
                                            if (exampleIndex.getClustered() != null && exampleIndex.getClustered()) {
                                                relation.getIndexes().add(exampleIndex);
                                            } else {
                                                stillToAdd.add(exampleIndex);
                                            }
                                        }

                                        var18 = stillToAdd.iterator();

                                        while (var18.hasNext()) {
                                            exampleIndex = (Index) var18.next();
                                            boolean alreadyAddedSimilar = false;
                                            Iterator var22 = relation.getIndexes().iterator();

                                            while (var22.hasNext()) {
                                                Index index = (Index) var22.next();
                                                if (DatabaseObjectComparatorFactory.getInstance().isSameObject(index, exampleIndex, (CompareControl.SchemaComparison[]) null, database)) {
                                                    alreadyAddedSimilar = true;
                                                }
                                            }

                                            if (!alreadyAddedSimilar) {
                                                relation.getIndexes().add(exampleIndex);
                                            }
                                        }
                                        break label122;
                                    }
                                }

                                row = (CachedRow) var9.next();
                                indexName = row.getString("INDEX_NAME");
                            } while (indexName == null);
                        } while (database instanceof AbstractDb2Database && "SYSIBM".equals(row.getString("INDEX_QUALIFIER")));

                        Index index = (Index) foundIndexes.get(indexName);
                        if (index == null) {
                            index = new Index();
                            index.setName(indexName);
                            index.setRelation(relation);
                            short type = row.getShort("TYPE");
                            if (type == 1) {
                                index.setClustered(true);
                            } else if (database instanceof MSSQLDatabase) {
                                index.setClustered(false);
                            }

                            foundIndexes.put(indexName, index);
                        }

                        String ascOrDesc;
                        if (database instanceof Db2zDatabase) {
                            ascOrDesc = row.getString("ORDER");
                        } else {
                            ascOrDesc = row.getString("ASC_OR_DESC");
                        }

                        Boolean descending = "D".equals(ascOrDesc) ? Boolean.TRUE : ("A".equals(ascOrDesc) ? Boolean.FALSE : null);
                        index.addColumn((new Column(row.getString("COLUMN_NAME"))).setComputed(false).setDescending(descending).setRelation(index.getRelation()));
                    }
                } catch (Exception var15) {
                    throw new DatabaseException(var15);
                }
            }

            Index exampleIndex;
            if (foundObject instanceof UniqueConstraint && ((UniqueConstraint) foundObject).getBackingIndex() == null && !(snapshot.getDatabase() instanceof DB2Database) && !(snapshot.getDatabase() instanceof DerbyDatabase)) {
                exampleIndex = (new Index()).setRelation(((UniqueConstraint) foundObject).getRelation());
                exampleIndex.getColumns().addAll(((UniqueConstraint) foundObject).getColumns());
                ((UniqueConstraint) foundObject).setBackingIndex(exampleIndex);
            }

            if (foundObject instanceof ForeignKey && ((ForeignKey) foundObject).getBackingIndex() == null) {
                exampleIndex = (new Index()).setRelation(((ForeignKey) foundObject).getForeignKeyTable());
                exampleIndex.getColumns().addAll(((ForeignKey) foundObject).getForeignKeyColumns());
                ((ForeignKey) foundObject).setBackingIndex(exampleIndex);
            }

        }
    }

    @Override
    protected DatabaseObject snapshotObject(DatabaseObject example, DatabaseSnapshot snapshot) throws DatabaseException, InvalidExampleException {
        Database database = snapshot.getDatabase();
        Relation exampleIndex = ((Index) example).getRelation();
        String tableName = null;
        Schema schema = null;
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

        String exampleName = example.getName();
        if (exampleName != null) {
            exampleName = database.correctObjectName(exampleName, Index.class);
        }

        Map<String, Index> foundIndexes = new HashMap();
        JdbcDatabaseSnapshot.CachingDatabaseMetaData databaseMetaData = null;
        List<CachedRow> rs = null;

        try {
            databaseMetaData = ((JdbcDatabaseSnapshot) snapshot).getMetaDataFromCache();
            rs = databaseMetaData.getIndexInfo(((AbstractJdbcDatabase) database).getJdbcCatalogName(schema), ((AbstractJdbcDatabase) database).getJdbcSchemaName(schema), tableName, exampleName);
            Iterator var11 = rs.iterator();

            label218:
            while (true) {
                while (true) {
                    CachedRow row;
                    String indexName;
                    String correctedIndexName;
                    short type;
                    Boolean nonUnique;
                    String columnName;
                    short position;
                    String definition;
                    do {
                        do {
                            do {
                                do {
                                    do {
                                        if (!var11.hasNext()) {
                                            break label218;
                                        }

                                        row = (CachedRow) var11.next();
                                        String rawIndexName = row.getString("INDEX_NAME");
                                        indexName = this.cleanNameFromDatabase(rawIndexName, database);
                                        correctedIndexName = database.correctObjectName(indexName, Index.class);
                                    } while (indexName == null);
                                } while (exampleName != null && !exampleName.equals(correctedIndexName));
                            } while (database instanceof InformixDatabase && indexName.startsWith(" "));

                            type = row.getShort("TYPE");
                            nonUnique = row.getBoolean("NON_UNIQUE");
                            if (nonUnique == null) {
                                nonUnique = true;
                            }

                            columnName = this.cleanNameFromDatabase(row.getString("COLUMN_NAME"), database);
                            position = row.getShort("ORDINAL_POSITION");
                            definition = StringUtil.trimToNull(row.getString("FILTER_CONDITION"));
                            if (definition != null && !(database instanceof OracleDatabase)) {
                                definition = definition.replaceAll("\"", "");
                            }
                        } while (columnName == null && definition == null);
                    } while (!(database instanceof H2Database) && type == 0);

                    if (database instanceof OracleDatabase && definition != null && columnName != null) {
                        String potentialColumnExpression = definition.replaceFirst("^\"?(.*?)\"?$", "$1");
                        OracleDatabase oracle = (OracleDatabase) database;
                        if (oracle.isValidOracleIdentifier(potentialColumnExpression, Index.class) && !oracle.isFunction(potentialColumnExpression)) {
                            columnName = potentialColumnExpression;
                            definition = null;
                        }
                    }

                    Index returnIndex = (Index) foundIndexes.get(correctedIndexName);
                    Object includedColumns;
                    if (returnIndex == null) {
                        returnIndex = new Index();
                        includedColumns = new Table();
                        if ("V".equals(row.getString("INTERNAL_OBJECT_TYPE"))) {
                            includedColumns = new View();
                        }

                        returnIndex.setRelation(((Relation) includedColumns).setName(row.getString("TABLE_NAME")).setSchema(schema));
                        returnIndex.setName(indexName);
                        returnIndex.setUnique(!nonUnique);
                        String tablespaceName = row.getString("TABLESPACE_NAME");
                        if (tablespaceName != null && database.supportsTablespaces()) {
                            returnIndex.setTablespace(tablespaceName);
                        }

                        if (type == 1) {
                            returnIndex.setClustered(true);
                        } else if (database instanceof MSSQLDatabase) {
                            returnIndex.setClustered(false);
                        }

                        if (database instanceof MSSQLDatabase) {

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
                        }

                        foundIndexes.put(correctedIndexName, returnIndex);
                    }

                    Object includedColumn = (row.containsColumn("IS_INCLUDED_COLUMN")) ?
                            row.containsColumn("IS_INCLUDED_COLUMN") : row.get("is_included_column");

                    if (database instanceof MSSQLDatabase && Boolean.TRUE.equals((includedColumn))) {

                        includedColumns = (List) returnIndex.getAttribute("includedColumns", List.class);
                        if (includedColumns == null) {
                            includedColumns = new ArrayList();
                            returnIndex.setAttribute("includedColumns", includedColumns);
                        }

                        ((List) includedColumns).add(columnName);
                    } else if (position != 0) {
                        for (int i = returnIndex.getColumns().size(); i < position; ++i) {
                            returnIndex.getColumns().add(null);
                        }

                        if (definition != null && !(database instanceof PostgresDatabase) && !(database instanceof MSSQLDatabase)) {
                            returnIndex.getColumns().set(position - 1, (new Column()).setRelation(returnIndex.getRelation()).setName(definition, true));
                        } else {
                            String ascOrDesc;
                            if (database instanceof Db2zDatabase) {
                                ascOrDesc = row.getString("ORDER");
                            } else {
                                ascOrDesc = row.getString("ASC_OR_DESC");
                            }

                            Boolean descending = "D".equals(ascOrDesc) ? Boolean.TRUE : ("A".equals(ascOrDesc) ? Boolean.FALSE : null);
                            returnIndex.getColumns().set(position - 1, (new Column(columnName)).setDescending(descending).setRelation(returnIndex.getRelation()));
                        }
                    }
                }
            }
        } catch (Exception var25) {
            throw new DatabaseException(var25);
        }

        if (exampleName != null) {
            Index index = (Index) foundIndexes.get(exampleName);
            return index;
        } else {
            List<Index> nonClusteredIndexes = new ArrayList();
            Iterator var29 = foundIndexes.values().iterator();

            while (var29.hasNext()) {
                Index index = (Index) var29.next();
                if (DatabaseObjectComparatorFactory.getInstance().isSameObject(index.getRelation(), exampleIndex, snapshot.getSchemaComparisons(), database)) {
                    boolean actuallyMatches = false;
                    if (database.isCaseSensitive()) {
                        if (index.getColumnNames().equals(((Index) example).getColumnNames())) {
                            actuallyMatches = true;
                        }
                    } else if (index.getColumnNames().equalsIgnoreCase(((Index) example).getColumnNames())) {
                        actuallyMatches = true;
                    }

                    if (actuallyMatches) {
                        if (index.getClustered() != null && index.getClustered()) {
                            return this.finalizeIndex(schema, tableName, index, snapshot);
                        }

                        nonClusteredIndexes.add(index);
                    }
                }
            }

            if (!nonClusteredIndexes.isEmpty()) {
                return this.finalizeIndex(schema, tableName, (Index) nonClusteredIndexes.get(0), snapshot);
            } else {
                return null;
            }
        }
    }

}
