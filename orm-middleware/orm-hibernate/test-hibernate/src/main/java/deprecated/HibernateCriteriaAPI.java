package deprecated;

import org.hibernate.Session;

public class HibernateCriteriaAPI {

    public Object testCriteriaAPI(Session session) {
        // Criteria criteria = session.createCriteria(User.class);
        // criteria.add(Restrictions.eq("field_name", "value")).uniqueResult()
        // criteria.setFetchMode("maturityItems", FetchMode.JOIN);
        // criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        // return criteria.uniqueResult();
        return null;
    }
}
