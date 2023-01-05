package main;

import liquibase.CatalogAndSchema;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.diff.output.DiffOutputControl;
import liquibase.exception.LiquibaseException;
import liquibase.integration.commandline.CommandLineUtils;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.snapshot.jvm.ColumnSnapshotGenerator;
import liquibase.snapshot.jvm.IndexSnapshotGenerator;
import main.snapshot.FilteringIndexSnapshotGeneratorTemp;
import main.snapshot.MyColumnSnapshotGenerator;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SchemaImportExportTester {

    private static String sqlServerConnectStr = "jdbc:sqlserver://localhost:1433;Database=liquibase-4-18;Trusted_Connection=true;useBulkCopyForBatchInsert=true;";
    private static String sqlServerRemote = "jdbc:sqlserver://dell719xxx:1433;Database=DAS_CONV_TOOL;Trusted_Connection=true;useBulkCopyForBatchInsert=true;";

    private static final String CHANGELOG_TIM = "orm-liquibase/liquibase-core-4-18/src/main/resources/changelog-tim.xml";
    private static final String CHANGELOG_REF = "orm-liquibase/liquibase-core-4-18/src/main/resources/changelog-ref.xml";

    private static final String CHANGELOG_PROC = "orm-liquibase/liquibase-core-4-18/src/main/resources/changelog-proc.xml";
    private static final String CHANGELOG_PROC_TEMP = "orm-liquibase/liquibase-core-4-18/src/main/resources/changelog-proc_temp.xml";

    public static void main(String[] args) throws Exception {
        SnapshotGeneratorFactory.getInstance().unregister(ColumnSnapshotGenerator.class);
        SnapshotGeneratorFactory.getInstance().register(new MyColumnSnapshotGenerator());
        SnapshotGeneratorFactory.getInstance().unregister(IndexSnapshotGenerator.class);
        SnapshotGeneratorFactory.getInstance().register(new FilteringIndexSnapshotGeneratorTemp());

        // getDBSchemaAndChangelogFiltered(CHANGELOG_REF, CHANGELOG_PROC_TEMP);
        // Thread.sleep(5000);
        importSchema();
        exportSchema();
    }

    private static void getDBSchemaAndChangelogFiltered(String changelogPath, String filteredChangelogPath) throws Exception {
        File changelogFile = new File(changelogPath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(changelogFile);

        createNewChangelog(doc, filteredChangelogPath);
    }

    private static void createNewChangelog(Document doc, String changelogProcessedPath) throws Exception {
        try (java.io.Writer writer = new FileWriter(changelogProcessedPath)) {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(writer);
            Source input = new DOMSource(doc);
            transformer.transform(input, output);
        }
    }

    private static void importSchema() throws Exception {
        Connection connection = DriverManager.getConnection(sqlServerConnectStr, "test", "TCHong18");
        JdbcConnection jdbcConnection = new JdbcConnection(connection);
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);

        try (Liquibase liquibase = new Liquibase("changelog-proc-from-db.xml", new ClassLoaderResourceAccessor(), database)) {
            liquibase.update(new Contexts(), new LabelExpression());
        }
    }

    private static void exportSchema() throws Exception {
        Connection connection = DriverManager.getConnection(sqlServerConnectStr, "test", "TCHong18");
        // Connection connection = DriverManager.getConnection(sqlServerRemote, "INSTAL", "INSTALL");
        JdbcConnection jdbcConnection = new JdbcConnection(connection);
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
        // Database database = createDatabase();

        CatalogAndSchema[] defaultCatalogAndSchema = new CatalogAndSchema[]{CatalogAndSchema.DEFAULT};
        DiffOutputControl requireTablespaceForDiff = getTableDiff();
        try {
            // Generated changelog written to changelog-proc.xml
            // 在从DB中提取Schema直接生成changset到指定的changelog文件中
            CommandLineUtils.doGenerateChangeLog("changelog-proc_4_5.xml", database, defaultCatalogAndSchema, null, "ctong", null, null, requireTablespaceForDiff);
        } finally {
            database.close();
        }
    }

    public static Database createDatabase() throws LiquibaseException, SQLException {
        Connection connection;
        try {
            connection = DriverManager.getConnection(sqlServerRemote, "INSTAL", "INSTALL");
        } catch (SQLException e) {
            throw new SQLException("Error in connection properties." + e, e);
        }
        JdbcConnection jdbcConnection = new JdbcConnection(connection);
        Liquibase liquibase = new Liquibase(null, null, jdbcConnection);
        return liquibase.getDatabase();
    }

    // 设置输出来的changelog是否包含catalogName和schemaName的信息
    // <createTable catalogName="DAS_CONV_TOOL" schemaName="MUREXDB" tableName="COLUMN_TYPE_TEST_T_10">
    private static DiffOutputControl getTableDiff() {
        DiffOutputControl diffOutputControl = new DiffOutputControl();
        diffOutputControl.setIncludeCatalog(false);
        diffOutputControl.setIncludeSchema(false);
        diffOutputControl.setIncludeTablespace(false);
        return diffOutputControl;
    }
}
