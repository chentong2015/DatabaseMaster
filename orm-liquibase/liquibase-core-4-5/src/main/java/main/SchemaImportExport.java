package main;

import liquibase.CatalogAndSchema;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.diff.output.DiffOutputControl;
import liquibase.integration.commandline.CommandLineUtils;
import liquibase.resource.ClassLoaderResourceAccessor;
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

public class SchemaImportExport {

    private static String sqlServerConnectStr = "jdbc:sqlserver://localhost:1433;Database=liquibase-4-5;Trusted_Connection=true;useBulkCopyForBatchInsert=true;";

    private static final String CHANGELOG_TIM = "orm-liquibase/liquibase-core-4-5/src/main/resources/changelog-tim.xml";
    private static final String CHANGELOG_PROC = "orm-liquibase/liquibase-core-4-5/src/main/resources/changelog-proc.xml";

    public static void main(String[] args) throws Exception {
        getDBSchemaAndChangelogFiltered(CHANGELOG_TIM, CHANGELOG_PROC);
        Thread.sleep(2000);
        importSchema();
        exportSchema();
        System.out.println("done");
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
        Connection connection = DriverManager.getConnection(sqlServerConnectStr, "test", "TCHong19");
        JdbcConnection jdbcConnection = new JdbcConnection(connection);
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);

        try (Liquibase liquibase = new Liquibase("changelog-proc.xml", new ClassLoaderResourceAccessor(), database)) {
            liquibase.update(new Contexts(), new LabelExpression());
        }
    }

    // Generated changelog written to changelog-proc.xml
    // 在从DB中提取Schema直接生成changset到指定的changelog文件中
    private static void exportSchema() throws Exception {
        Connection connection = DriverManager.getConnection(sqlServerConnectStr, "test", "TCHong19");
        JdbcConnection jdbcConnection = new JdbcConnection(connection);
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);

        CatalogAndSchema[] defaultCatalogAndSchema = new CatalogAndSchema[]{CatalogAndSchema.DEFAULT};
        DiffOutputControl requireTablespaceForDiff = getTableDiff();
        try {
            CommandLineUtils.doGenerateChangeLog("changelog-proc.xml", database, defaultCatalogAndSchema, null, "ctong", null, null, requireTablespaceForDiff);
        } finally {
            database.close();
        }
    }

    private static DiffOutputControl getTableDiff() {
        DiffOutputControl diffOutputControl = new DiffOutputControl();
        diffOutputControl.setIncludeCatalog(false);
        diffOutputControl.setIncludeSchema(false);
        diffOutputControl.setIncludeTablespace(false);
        return diffOutputControl;
    }
}
