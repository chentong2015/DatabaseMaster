package main.snapshot;

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
import liquibase.structure.core.DataType;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Schema;

import java.sql.SQLException;
import java.util.List;
import java.util.TreeMap;

public class MyColumnSnapshotGenerator extends ColumnSnapshotGenerator {

    private static final String ORDINAL_POSITION = "ORDINAL_POSITION";
    private static final String COLUMN_SIZE = "COLUMN_SIZE";
    private static final String DECIMAL_DIGITS = "DECIMAL_DIGITS";

    private List<CachedRow> allColumnsMetadataRs = null;

    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        return PRIORITY_DATABASE;
    }

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

        Schema schema = relation.getSchema();
        allColumnsMetadataRs = databaseMetaData.getColumns(database.getJdbcCatalogName(schema), database.getJdbcSchemaName(schema), relation.getName(), null);

        TreeMap<Integer, CachedRow> treeSet = new TreeMap<>();
        for (CachedRow row : allColumnsMetadataRs) {
            treeSet.put(row.getInt(ORDINAL_POSITION), row);
        }
        Logger log = Scope.getCurrentScope().getLog(getClass());
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

    @Override
    protected DataType readDataType(CachedRow columnMetadataResultSet, Column column, Database database) throws DatabaseException {
        if (!(database instanceof MSSQLDatabase)) {
            return super.readDataType(columnMetadataResultSet, column, database);
        }

        String columnTypeName = parseColumnTypeName(columnMetadataResultSet);

        int dataType;
        if (columnMetadataResultSet.containsColumn("DATA_TYPE")) {
            dataType = columnMetadataResultSet.getInt("DATA_TYPE");
        } else {
            dataType = columnMetadataResultSet.getInt("data_type");
        }

        Integer columnSize = null;
        Integer decimalDigits = null;
        if (!database.dataTypeIsNotModifiable(columnTypeName)) {
            columnSize = columnMetadataResultSet.getInt(COLUMN_SIZE);
            decimalDigits = columnMetadataResultSet.getInt(DECIMAL_DIGITS);
            if (decimalDigits != null && decimalDigits.equals(0) && dataType != 92) {
                decimalDigits = null;
            }
        }

        DataType type = new DataType(columnTypeName);
        type.setDataTypeId(dataType);
        if (dataType == 93) {
            if (decimalDigits == null) {
                type.setColumnSize(null);
            } else {
                type.setColumnSize(decimalDigits != database.getDefaultFractionalDigitsForTimestamp() ? decimalDigits : null);
            }
            type.setDecimalDigits(null);
        } else {
            type.setColumnSize(columnSize);
            type.setDecimalDigits(decimalDigits);
        }

        type.setRadix(columnMetadataResultSet.getInt("NUM_PREC_RADIX"));
        type.setCharacterOctetLength(columnMetadataResultSet.getInt("CHAR_OCTET_LENGTH"));
        type.setColumnSizeUnit(DataType.ColumnSizeUnit.BYTE);
        return type;
    }

    private String parseColumnTypeName(CachedRow columnMetadataResultSet) {
        String columnTypeName = (String) columnMetadataResultSet.get("TYPE_NAME");
        if ("numeric() identity".equalsIgnoreCase(columnTypeName)) {
            columnTypeName = "numeric";
        } else if ("decimal() identity".equalsIgnoreCase(columnTypeName)) {
            columnTypeName = "decimal";
        } else if ("xml".equalsIgnoreCase(columnTypeName)) {
            columnMetadataResultSet.set(COLUMN_SIZE, null);
            columnMetadataResultSet.set(DECIMAL_DIGITS, null);
        } else if ("datetimeoffset".equalsIgnoreCase(columnTypeName) || "time".equalsIgnoreCase(columnTypeName)) {
            columnMetadataResultSet.set(COLUMN_SIZE, columnMetadataResultSet.getInt(DECIMAL_DIGITS));
            columnMetadataResultSet.set(DECIMAL_DIGITS, null);
        }
        return columnTypeName;
    }
}