package metadata;

import metadata.entity.LabelEntity;
import metadata.entity.TradeEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class MetadataSourcesTest {

    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        MetadataSources metadataSources = new MetadataSources();
        metadataSources.addAnnotatedClass(TradeEntity.class).addAnnotatedClass(LabelEntity.class);

        MetadataBuilder metadataBuilder = metadataSources.getMetadataBuilder(registry);
        Metadata metadata = metadataBuilder.build();
        SessionFactory sessionFactory = metadata.buildSessionFactory();
        Session session = sessionFactory.openSession();

        String fullQuery = "FROM " + TradeEntity.class.getCanonicalName();
        List<TradeEntity> tradeEntities = session.createQuery(fullQuery, TradeEntity.class).getResultList();
        System.out.println(tradeEntities.size());
        sessionFactory.close();
    }
}
