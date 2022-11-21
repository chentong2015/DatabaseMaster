package com.liquibase.main.extensions.database;

import liquibase.CatalogAndSchema;
import liquibase.change.Change;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.RanChangeSet;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.exception.*;
import liquibase.sql.visitor.SqlVisitor;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.SqlStatement;
import liquibase.structure.DatabaseObject;

import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

// 自定义实现Database的逻辑
public class MyDatabase implements Database {

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection databaseConnection) throws DatabaseException {
        return false;
    }

    @Override
    public String getDefaultDriver(String s) {
        return null;
    }

    @Override
    public DatabaseConnection getConnection() {
        return null;
    }

    @Override
    public void setConnection(DatabaseConnection databaseConnection) {

    }

    @Override
    public boolean requiresUsername() {
        return false;
    }

    @Override
    public boolean requiresPassword() {
        return false;
    }

    @Override
    public boolean getAutoCommitMode() {
        return false;
    }

    @Override
    public boolean supportsDDLInTransaction() {
        return false;
    }

    @Override
    public String getDatabaseProductName() {
        return null;
    }

    @Override
    public String getDatabaseProductVersion() throws DatabaseException {
        return null;
    }

    @Override
    public int getDatabaseMajorVersion() throws DatabaseException {
        return 0;
    }

    @Override
    public int getDatabaseMinorVersion() throws DatabaseException {
        return 0;
    }

    @Override
    public String getShortName() {
        return "my database short name";
    }

    @Override
    public String getDefaultCatalogName() {
        return null;
    }

    @Override
    public void setDefaultCatalogName(String s) throws DatabaseException {

    }

    @Override
    public String getDefaultSchemaName() {
        return null;
    }

    @Override
    public Integer getDefaultScaleForNativeDataType(String s) {
        return null;
    }

    @Override
    public void setDefaultSchemaName(String s) throws DatabaseException {

    }

    @Override
    public Integer getDefaultPort() {
        return null;
    }

    @Override
    public Integer getFetchSize() {
        return null;
    }

    @Override
    public String getLiquibaseCatalogName() {
        return null;
    }

    @Override
    public void setLiquibaseCatalogName(String s) {

    }

    @Override
    public String getLiquibaseSchemaName() {
        return null;
    }

    @Override
    public void setLiquibaseSchemaName(String s) {

    }

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return false;
    }

    @Override
    public boolean supportsSequences() {
        return false;
    }

    @Override
    public boolean supportsDropTableCascadeConstraints() {
        return false;
    }

    @Override
    public boolean supportsAutoIncrement() {
        return false;
    }

    @Override
    public String getDateLiteral(String s) {
        return null;
    }

    @Override
    public String getCurrentDateTimeFunction() {
        return null;
    }

    @Override
    public void setCurrentDateTimeFunction(String s) {

    }

    @Override
    public String getLineComment() {
        return null;
    }

    @Override
    public String getAutoIncrementClause(BigInteger bigInteger, BigInteger bigInteger1, String s, Boolean aBoolean) {
        return null;
    }

    @Override
    public String getDatabaseChangeLogTableName() {
        return null;
    }

    @Override
    public void setDatabaseChangeLogTableName(String s) {

    }

    @Override
    public String getDatabaseChangeLogLockTableName() {
        return null;
    }

    @Override
    public void setDatabaseChangeLogLockTableName(String s) {

    }

    @Override
    public String getLiquibaseTablespaceName() {
        return null;
    }

    @Override
    public void setLiquibaseTablespaceName(String s) {

    }

    @Override
    public String getConcatSql(String... strings) {
        return null;
    }

    @Override
    public void setCanCacheLiquibaseTableInfo(boolean b) {

    }

    @Override
    public void dropDatabaseObjects(CatalogAndSchema catalogAndSchema) throws LiquibaseException {

    }

    @Override
    public void tag(String s) throws DatabaseException {

    }

    @Override
    public boolean doesTagExist(String s) throws DatabaseException {
        return false;
    }

    @Override
    public boolean isSystemObject(DatabaseObject databaseObject) {
        return false;
    }

    @Override
    public boolean isLiquibaseObject(DatabaseObject databaseObject) {
        return false;
    }

    @Override
    public String getViewDefinition(CatalogAndSchema catalogAndSchema, String s) throws DatabaseException {
        return null;
    }

    @Override
    public String getDateLiteral(Date date) {
        return null;
    }

    @Override
    public String getTimeLiteral(Time time) {
        return null;
    }

    @Override
    public String getDateTimeLiteral(Timestamp timestamp) {
        return null;
    }

    @Override
    public String getDateLiteral(java.util.Date date) {
        return null;
    }

    @Override
    public String escapeObjectName(String s, String s1, String s2, Class<? extends DatabaseObject> aClass) {
        return null;
    }

    @Override
    public String escapeTableName(String s, String s1, String s2) {
        return null;
    }

    @Override
    public String escapeIndexName(String s, String s1, String s2) {
        return null;
    }

    @Override
    public String escapeObjectName(String s, Class<? extends DatabaseObject> aClass) {
        return null;
    }

    @Override
    public int getMaxFractionalDigitsForTimestamp() {
        return 0;
    }

    @Override
    public int getDefaultFractionalDigitsForTimestamp() {
        return 0;
    }

    @Override
    public String escapeColumnName(String s, String s1, String s2, String s3) {
        return null;
    }

    @Override
    public String escapeColumnName(String s, String s1, String s2, String s3, boolean b) {
        return null;
    }

    @Override
    public String escapeColumnNameList(String s) {
        return null;
    }

    @Override
    public boolean supportsTablespaces() {
        return false;
    }

    @Override
    public boolean supportsCatalogs() {
        return false;
    }

    @Override
    public CatalogAndSchema.CatalogAndSchemaCase getSchemaAndCatalogCase() {
        return null;
    }

    @Override
    public boolean supportsSchemas() {
        return false;
    }

    @Override
    public boolean supportsCatalogInObjectName(Class<? extends DatabaseObject> aClass) {
        return false;
    }

    @Override
    public String generatePrimaryKeyName(String s) {
        return null;
    }

    @Override
    public String escapeSequenceName(String s, String s1, String s2) {
        return null;
    }

    @Override
    public String escapeViewName(String s, String s1, String s2) {
        return null;
    }

    @Override
    public ChangeSet.RunStatus getRunStatus(ChangeSet changeSet) throws DatabaseException, DatabaseHistoryException {
        return null;
    }

    @Override
    public RanChangeSet getRanChangeSet(ChangeSet changeSet) throws DatabaseException, DatabaseHistoryException {
        return null;
    }

    @Override
    public void markChangeSetExecStatus(ChangeSet changeSet, ChangeSet.ExecType execType) throws DatabaseException {

    }

    @Override
    public List<RanChangeSet> getRanChangeSetList() throws DatabaseException {
        return null;
    }

    @Override
    public java.util.Date getRanDate(ChangeSet changeSet) throws DatabaseException, DatabaseHistoryException {
        return null;
    }

    @Override
    public void removeRanStatus(ChangeSet changeSet) throws DatabaseException {

    }

    @Override
    public void commit() throws DatabaseException {

    }

    @Override
    public void rollback() throws DatabaseException {

    }

    @Override
    public String escapeStringForDatabase(String s) {
        return null;
    }

    @Override
    public void close() throws DatabaseException {

    }

    @Override
    public boolean supportsRestrictForeignKeys() {
        return false;
    }

    @Override
    public String escapeConstraintName(String s) {
        return null;
    }

    @Override
    public boolean isAutoCommit() throws DatabaseException {
        return false;
    }

    @Override
    public void setAutoCommit(boolean b) throws DatabaseException {

    }

    @Override
    public boolean isSafeToRunUpdate() throws DatabaseException {
        return false;
    }

    @Override
    public void executeStatements(Change change, DatabaseChangeLog databaseChangeLog, List<SqlVisitor> list) throws LiquibaseException {

    }

    @Override
    public void execute(SqlStatement[] sqlStatements, List<SqlVisitor> list) throws LiquibaseException {

    }

    @Override
    public void saveStatements(Change change, List<SqlVisitor> list, Writer writer) throws IOException, LiquibaseException {

    }

    @Override
    public void executeRollbackStatements(Change change, List<SqlVisitor> list) throws LiquibaseException {

    }

    @Override
    public void executeRollbackStatements(SqlStatement[] sqlStatements, List<SqlVisitor> list) throws LiquibaseException {

    }

    @Override
    public void saveRollbackStatement(Change change, List<SqlVisitor> list, Writer writer) throws IOException, LiquibaseException {

    }

    @Override
    public java.util.Date parseDate(String s) throws DateParseException {
        return null;
    }

    @Override
    public List<DatabaseFunction> getDateFunctions() {
        return null;
    }

    @Override
    public void resetInternalState() {

    }

    @Override
    public boolean supportsForeignKeyDisable() {
        return false;
    }

    @Override
    public boolean disableForeignKeyChecks() throws DatabaseException {
        return false;
    }

    @Override
    public void enableForeignKeyChecks() throws DatabaseException {

    }

    @Override
    public boolean isCaseSensitive() {
        return false;
    }

    @Override
    public boolean isReservedWord(String s) {
        return false;
    }

    @Override
    public CatalogAndSchema correctSchema(CatalogAndSchema catalogAndSchema) {
        return null;
    }

    @Override
    public String correctObjectName(String s, Class<? extends DatabaseObject> aClass) {
        return null;
    }

    @Override
    public boolean isFunction(String s) {
        return false;
    }

    @Override
    public int getDataTypeMaxParameters(String s) {
        return 0;
    }

    @Override
    public CatalogAndSchema getDefaultSchema() {
        return null;
    }

    @Override
    public boolean dataTypeIsNotModifiable(String s) {
        return false;
    }

    @Override
    public String generateDatabaseFunctionValue(DatabaseFunction databaseFunction) {
        return null;
    }

    @Override
    public ObjectQuotingStrategy getObjectQuotingStrategy() {
        return null;
    }

    @Override
    public void setObjectQuotingStrategy(ObjectQuotingStrategy objectQuotingStrategy) {

    }

    @Override
    public boolean createsIndexesForForeignKeys() {
        return false;
    }

    @Override
    public boolean getOutputDefaultSchema() {
        return false;
    }

    @Override
    public void setOutputDefaultSchema(boolean b) {

    }

    @Override
    public boolean isDefaultSchema(String s, String s1) {
        return false;
    }

    @Override
    public boolean isDefaultCatalog(String s) {
        return false;
    }

    @Override
    public boolean getOutputDefaultCatalog() {
        return false;
    }

    @Override
    public void setOutputDefaultCatalog(boolean b) {

    }

    @Override
    public boolean supportsPrimaryKeyNames() {
        return false;
    }

    @Override
    public boolean supportsNotNullConstraintNames() {
        return false;
    }

    @Override
    public boolean supportsBatchUpdates() throws DatabaseException {
        return false;
    }

    @Override
    public boolean requiresExplicitNullForColumns() {
        return false;
    }

    @Override
    public String getSystemSchema() {
        return null;
    }

    @Override
    public void addReservedWords(Collection<String> collection) {

    }

    @Override
    public String escapeDataTypeName(String s) {
        return null;
    }

    @Override
    public String unescapeDataTypeName(String s) {
        return null;
    }

    @Override
    public String unescapeDataTypeString(String s) {
        return null;
    }

    @Override
    public ValidationErrors validate() {
        return null;
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
