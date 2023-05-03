package deprecated;

import org.hibernate.Criteria;
import org.hibernate.Session;

public class HibernateCriteriaAPI {

    public Object testCriteriaAPI(Session session) {
        Criteria criteria = session.createCriteria("entity_name");
        // criteria.setFetchMode("maturityItems", FetchMode.JOIN);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.uniqueResult();
    }
}
