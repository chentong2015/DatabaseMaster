package statistics;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

// Statistics provides the statistics of Hibernate SessionFactory
// Statistics are disabled by default for better performance
// 1. 支持获取数据库Fetching的统计(向数据库请求获取数据的count)
// 2. 支持获取二级缓存相关的统计信息
public class HibernateStatistics {

    private static Statistics statistics;

    public static void open(SessionFactory sessionFactory) {
        statistics = sessionFactory.getStatistics();
        statistics.setStatisticsEnabled(true);
        System.out.println("Stats enabled= " + statistics.isStatisticsEnabled());
    }

    // hit: 击中，从二级缓存中查找到数据
    // miss:错过，在二级缓存中没有找到
    // put: 添加，在二级缓存中添加数据
    public static void print(int step) {
        System.out.println("***** " + step + " *****");
        System.out.println("Fetch Count = " + statistics.getEntityFetchCount());
        System.out.println("Second Level Hit Count = " + statistics.getSecondLevelCacheHitCount());
        System.out.println("Second Level Miss Count = " + statistics.getSecondLevelCacheMissCount());
        System.out.println("Second Level Put Count = " + statistics.getSecondLevelCachePutCount());
    }
}
