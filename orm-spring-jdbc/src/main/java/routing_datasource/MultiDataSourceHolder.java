package routing_datasource;

// 持有当前线程上下文
public class MultiDataSourceHolder {

    private static final ThreadLocal<String> dataSourceHolder = new ThreadLocal<>();
    private static final ThreadLocal<String> tableIndexHolder = new ThreadLocal<>();

    public static void setDataSourceHolder(String key) {
        dataSourceHolder.set(key);
    }

    public static String getDataSource() {
        return dataSourceHolder.get();
    }

    public static void clearDataSourceKey() {
        dataSourceHolder.remove();
    }

    public static void setTableIndex(String key) {
        tableIndexHolder.set(key);
    }

    public static String getTableIndex() {
        return tableIndexHolder.get();
    }

    public static void clearTableIndex() {
        tableIndexHolder.remove();
    }
}
