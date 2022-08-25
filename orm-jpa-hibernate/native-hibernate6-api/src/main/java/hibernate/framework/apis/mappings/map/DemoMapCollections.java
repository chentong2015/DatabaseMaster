package hibernate.framework.apis.mappings.map;

import hibernate.framework.apis.session.HibernateSessionUtil;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.Map;

public class DemoMapCollections {

    public static void main(String[] args) {
        Session session = HibernateSessionUtil.getSession();
        session.getTransaction().begin();

        Map<RightsType, PermissionsEntity> map1 = new HashMap<>();
        map1.put(RightsType.LIQUIDITY_HORIZON_SET, new PermissionsEntity("permission 1"));
        map1.put(RightsType.NMRF_SET, new PermissionsEntity("permission 2"));
        RightsEntity entity1 = new RightsEntity(1, "group1");
        entity1.setPermissionsMap(map1);

        Map<RightsType, PermissionsEntity> map2 = new HashMap<>();
        map1.put(RightsType.REDUCED_SET, new PermissionsEntity("permission 11"));
        map1.put(RightsType.NMRF_SET, new PermissionsEntity("permission 22"));
        RightsEntity entity2 = new RightsEntity(2, "group2");
        entity2.setPermissionsMap(map2);

        session.persist(entity1);
        session.persist(entity2);
        session.getTransaction().commit();
        HibernateSessionUtil.closeSession();
    }
}
