package entity;

import entity.model.LabelEntity;
import entity.model.OspRightsMatcher;
import entity.model.OspRightsMatcherTask;
import entity.model.TradeEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class DemoHibernateEntityTest {

    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        MetadataSources metadataSources = new MetadataSources();
        metadataSources.addAnnotatedClass(OspRightsMatcher.class)
                .addAnnotatedClass(OspRightsMatcherTask.class)
                .addAnnotatedClass(TradeEntity.class)
                .addAnnotatedClass(LabelEntity.class);

        MetadataBuilder metadataBuilder = metadataSources.getMetadataBuilder(registry);
        Metadata metadata = metadataBuilder.build();
        SessionFactory sessionFactory = metadata.buildSessionFactory();
        Session session = sessionFactory.openSession();

        // String fullQuery = "FROM " + TradeEntity.class.getCanonicalName();
        // List<TradeEntity> tradeEntities = session.createQuery(fullQuery, TradeEntity.class).getResultList();
        // System.out.println(tradeEntities.size());

        String query = "select RTRIM(M_LABEL) from t_trade_entity where M_REFERENCE in (select M_REFERENCE from t_label_entity where M_IS_VALID=1)";
        List<String> result = session.createNativeQuery(query).getResultList();
        System.out.println(result.size());
        System.out.println(result.get(0));
        System.out.println(result.get(1));

        sessionFactory.close();
    }
}
