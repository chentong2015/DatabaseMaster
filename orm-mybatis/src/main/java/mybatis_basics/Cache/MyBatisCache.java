package mybatis_basics.Cache;

// 缓存特点
// 1. 优点：本地缓存不需要走网络IO和磁盘IO，提升查询效率
// 2. 缺点: 数据库更换造成缓存数据一致性问题 ==> "缓存双写一致性问题"

// MyBatis分一级缓存和二级缓存
// 1. 默认情况下，只启用了本地的会话缓存，它仅仅对一个会话中的数据进行缓存
// 2. 二级缓存是事务性的，当SqlSession完成并提交，或完成并回滚但没有执行flushCache=true的insert/delete/update语句时，缓存会获得更新
public class MyBatisCache {

    // 启用全局的二级缓存，只需要在SQL映射文件中添加一行<cache/>
    // <cache
    //  eviction="FIFO"
    //  flushInterval="60000"
    //  size="512"
    //  readOnly="true"/>
}
