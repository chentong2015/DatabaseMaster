package main;

import liquibase.Scope;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.exception.DatabaseException;
import liquibase.logging.Logger;
import liquibase.snapshot.CachedRow;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.JdbcDatabaseSnapshot;
import liquibase.snapshot.jvm.ColumnSnapshotGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Schema;

import java.sql.SQLException;
import java.util.List;
import java.util.TreeMap;

public class MyColumnSnapshotGenerator extends ColumnSnapshotGenerator {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    private static final String ORDINAL_POSITION = "ORDINAL_POSITION";

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private List<CachedRow> allColumnsMetadataRs = null;

    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        return PRIORITY_DATABASE;
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    @Override
    protected void addTo(DatabaseObject foundObject, DatabaseSnapshot snapshot) throws DatabaseException {
        var jdbcSnapshot = (JdbcDatabaseSnapshot) snapshot;
        if (!(jdbcSnapshot.getDatabase() instanceof MSSQLDatabase)) {
            super.addTo(foundObject, snapshot);
            return;
        }

        if (!snapshot.getSnapshotControl().shouldInclude(Column.class)) {
            return;
        }
        if (foundObject instanceof Relation) {
            Database database = snapshot.getDatabase();
            Relation relation = (Relation) foundObject;
            if (allColumnsMetadataRs == null) {
                // This is where we override Liquibase's behavior.
                // Only if we don't have the global metadata info, we will go and fetch them.
                // The output will then be cached into allColumnsMetadataRs, in order to avoid fetching them again.
                // This way we guarantee that this operation will be executed only once.
                try {
                    repairTableStructure(jdbcSnapshot, (AbstractJdbcDatabase) database, relation);
                } catch (SQLException e) {
                    throw new DatabaseException(e);
                }
            }

            try {
                addColumn(snapshot, database, relation);
            } catch (SQLException e) {
                throw new DatabaseException(e);
            }
        }
    }

    private void addColumn(DatabaseSnapshot snapshot, Database database, Relation relation) throws SQLException, DatabaseException {
        for (CachedRow row : allColumnsMetadataRs) {
            Column column = readColumn(row, relation, database);
            setAutoIncrementDetails(column, database, snapshot);
            column.setAttribute("liquibase-complete", true);
            relation.getColumns().add(column);
        }
    }

    private void repairTableStructure(JdbcDatabaseSnapshot snapshot, AbstractJdbcDatabase database, Relation relation) throws SQLException, DatabaseException {
        JdbcDatabaseSnapshot.CachingDatabaseMetaData databaseMetaData = snapshot.getMetaDataFromCache();

        Schema schema;

        schema = relation.getSchema();
        allColumnsMetadataRs = databaseMetaData.getColumns(database.getJdbcCatalogName(schema), database.getJdbcSchemaName(schema), relation.getName(), null);

        /*
         * COMMENT FROM LIQUIBASE
         *
         * Microsoft SQL Server, SAP SQL Anywhere and probably other RDBMS guarantee non-duplicate
         * ORDINAL_POSITIONs for the columns of a single table. But they do not guarantee there are no gaps
         * in that integers (e.g. if columns have been deleted). So we need to check for that and renumber
         * if needed.
         */
        TreeMap<Integer, CachedRow> treeSet = new TreeMap<>();
        for (CachedRow row : allColumnsMetadataRs) {
            treeSet.put(row.getInt(ORDINAL_POSITION), row);
        }
        Logger log = Scope.getCurrentScope().getLog(getClass());
        // COMMENT FROM LIQUIBASE
        //
        // Now we can iterate through the sorted list and repair if needed.
        int currentOrdinal = 0;
        for (CachedRow row : treeSet.values()) {
            currentOrdinal++;
            int rsOrdinal = row.getInt(ORDINAL_POSITION);
            if (rsOrdinal != currentOrdinal) {
                log.fine(String.format("Repairing %s with gaps for table=%s, column name=%s, " +
                        "bad ordinal=%d, new ordinal=%d", ORDINAL_POSITION, relation.getName(), row.getString("COLUMN_NAME"), rsOrdinal, currentOrdinal));
                row.set(ORDINAL_POSITION, currentOrdinal);
            }
        }
    }
}