package routing_datasource.dataModel;

import routing_datasource.MultiDataSourceHolder;

public class BaseDomin {

    private String tableSuffix;

    public String getTableSuffix() {
        return MultiDataSourceHolder.getTableIndex();
    }

    public void setTableSuffix(String tableSuffix) {
        this.tableSuffix = tableSuffix;
    }
}
