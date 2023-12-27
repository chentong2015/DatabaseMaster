package com.mybatis.basic.config.cache;

import com.mybatis.basic.model.Blog;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

// MyBatis分一级缓存和二级缓存
// 1. 优点：本地缓存不需要走网络IO和磁盘IO，提升查询效率
// 2. 缺点: "缓存双写一致性问题"，数据库更换造成缓存数据一致性问题

// TODO: 默认情况下，缓存都是开启的，但是二级缓存需要自定义配置实现(二级缓存的优先级更高)
// 1. 一级缓存(本地的会话缓存)，它仅仅对一个会话中的数据进行缓存
// 2. 二级缓存是事务性的，当SqlSession完成并提交，或完成并回滚但没有执行flushCache=true的insert/delete/update语句时，缓存会获得更新
// 3. SQL映射文件中添加一行<cache/>, 启用全局的二级缓存
//    <cache eviction="FIFO" flushInterval="60000" size="512" readOnly="true"/>
public class MyBatisCache {

    // TODO: 使用4个参数封装成CacheKey缓存key值：hashCode + sql id + sql...
    // CacheKey key = this.createCacheKey(ms, parameterObject, rowBounds, boundSql);

    // SimpleExecutor > BaseExecutor.query() 调用的方法
    //  public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSql boundSql) throws SQLException {
    //    orContext.instance().resource(ms.getResource()).activity("executing a query").object(ms.getId());
    //    (this.closed) {
    //     throw new ExecutorException("Executor was closed.");
    //    lse {
    //     if (this.queryStack == 0 && ms.isFlushCacheRequired()) {
    //         this.clearLocalCache();
    //     }
    //     List list;
    //     try {
    //         ++this.queryStack;
    //         // TODO: 缓存非空则从本地缓存中取获取，反之从数据库中获取
    //         list = resultHandler == null ? (List)this.localCache.getObject(key) : null;
    //         if (list != null) {
    //             this.handleLocallyCachedOutputParameters(ms, key, parameter, boundSql);
    //         } else {
    //             list = this.queryFromDatabase(ms, parameter, rowBounds, resultHandler, key, boundSql);
    //         }
    //     } finally {
    //         --this.queryStack;
    //     }
    //     ...

    //  BaseExecutor.queryFromDatabase()
    //  this.doQuery()查询完成之后，保证清除本地的缓存中指定的key，然后在.putObject(key, list)设置key
    //  private <E> List<E> queryFromDatabase(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSql boundSql) throws SQLException {
    //        this.localCache.putObject(key, ExecutionPlaceholder.EXECUTION_PLACEHOLDER);
    //        List list;
    //        try {
    //            list = this.doQuery(ms, parameter, rowBounds, resultHandler, boundSql);
    //        } finally {
    //            this.localCache.removeObject(key);
    //        }
    //        this.localCache.putObject(key, list);
    //        if (ms.getStatementType() == StatementType.CALLABLE) {
    //            this.localOutputParameterCache.putObject(key, parameter);
    //        }
    //        return list;
    //    }

    public static void main(String[] args) throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        String statement = "mybatis_basics.testConfigXml.BlogMapper.selectBlog";
        // 1. 第二次查询时，直接从缓存中取数据
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Blog blog1 = session.selectOne(statement, 1);
            Blog blog2 = session.selectOne(statement, 1);
            System.out.println(blog1.getName());
            System.out.println(blog2.getName());
        }

        // 2. 第二次查询时，如果缓存中找不到，则查询数据库
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Blog blog1 = session.selectOne(statement, 1);
            Blog blog2 = session.selectOne(statement, 2);
            System.out.println(blog1.getName());
            System.out.println(blog2.getName());
        }

        // 3. 如果Session会话已经Commit，则下次查询时将重新走数据库
        SqlSession session = sqlSessionFactory.openSession();
        Blog blog1 = session.selectOne(statement, 1);
        session.commit();
        Blog blog2 = session.selectOne(statement, 1);
        System.out.println(blog1.getName());
        System.out.println(blog2.getName());
        session.close();
    }
}
