package middleware.connection.pool.demo3;

import java.sql.SQLException;

// 连接池中"数据库连接对象"需要考虑，从创建到释放(不再使用)之间花了多久的时间
// - 数据库连接被创建的时间
// - 在数据库连接创建时记录开始时间
// - 在数据库连接使用完，被释放时，记录释放时间
// - 统计这个数据库连接被使用的次数
// - 判断这个数据库连接是否可用
public abstract class PoolableConnection {

    private long creationTime;
    private long startUsingTime;
    private long freeTime;

    private int usedCount;
    private boolean isFree;

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setStartUsingTime(long startUsingTime) {
        this.startUsingTime = startUsingTime;
        this.isFree = false;
    }

    public long getStartUsingTime() {
        return startUsingTime;
    }

    public void setFreeTime(long freeTime) {
        this.freeTime = freeTime;
        this.isFree = true;
    }

    public long getFreeTime() {
        return freeTime;
    }

    public void incrementUsedCount() {
        ++usedCount;
    }

    public int getUsedCount() {
        return usedCount;
    }

    abstract void destroy() throws SQLException;

}
