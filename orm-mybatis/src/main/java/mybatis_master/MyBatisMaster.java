package mybatis_master;

// TODO: 对ORM架构源码级别的理解
// https://www.bilibili.com/video/BV1QA411A7q1
// https://www.bilibili.com/video/BV1U64y1W7fw
public class MyBatisMaster {

    /**
     * 1. SqlSessionFactoryBuilder: 实例化之后，创建SqlSessionFactory之后则不再需要，最好关闭(保证在方法局部作用域)
     * 2. SqlSessionFactory: 创建之后，应该在应用的运行期间一直存在, 不应该丢弃或者创建新的实例
     * .                     应用作用域, 使用单例模式或者静态单例模式
     * 3. SqlSession: 每个线程都有它自己的SqlSession实例, 不是线程安全的, 不能共享
     * .              一般使用try-resource声明, 保证所有数据库资源都能被正确地关闭
     * .              不能将SqlSession实例的引用放在一个类的静态域，或者类型的托管作用域中
     * .              使用Web框架, 应该将SqlSession放在和HTTP请求相似的作用域中, 收到请求时打开, 返回响应后关闭 !
     * 4. Mapper: 映射器接口的实例是从SqlSession中获得的, 最好放在方法作用域内, 映射器实例并不需要被显式地关闭
     * .          MyBatis 创建结果对象的新实例时, 它都会使用一个对象工厂(ObjectFactory)实例来完成实例化工作 ! 可以自定义
     */
}
