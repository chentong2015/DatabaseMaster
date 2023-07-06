package multi_id_class;

import multi_id_class.entity.TradeRefreshAuditRecordDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Date;

import static multi_id_class.query_builder.EntityId.TRADE_EXT;

public class DemoMultiIdEntity {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main1(String[] args) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();

        final TradeRefreshAuditRecordDTO record = new TradeRefreshAuditRecordDTO();
        record.setTradeNumber(10L);
        record.setTimestamp(new Date());
        record.setSource(1);
        record.setStatus("PLANNED");
        record.setAuditKey(0L);
        session.persist(record);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public static void main(String[] args) {
        final StringBuilder fullQuery = new StringBuilder("select distinct ")
                .append(TRADE_EXT).append(".tradeReference");
        System.out.println(fullQuery);
    }
}
