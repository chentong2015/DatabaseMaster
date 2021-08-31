package JdbcTemplateJdcp.base;

public interface InformationDao {

    boolean insertInformation(Information info);

    Information getInformation(int id);

    void cleanupTable();
}
