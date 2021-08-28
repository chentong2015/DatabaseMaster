public class DemoHibernate {

    // 连接项目所需要的数据库 SQL Server
    // 1. 连接项目所需要的数据库，走网络端口去要数据 !!
    // 2. 打开SQL Server的1433默认的端口
    //    SQL Server Configuration Manager > SQL Server Networking Configuration > Protocol > TCP/IP > Enabled开启 > 重启
    // 3. 打开本地计算机防火墙的1433端口
    //    防火墙 > 高级设置 > Inbound Rules入站规则(外部通过网络访问时，是否允许) > New Rule > TCP 1433 > Allow Connection
    //    防火墙 > 高级设置 > Outbound Rules出站规则(内部数据通过是否允许从指定端口出去) > New > TCP 1433 > Allow Connection
    // 4. 添加JDBC Driver Jar 驱动库
    //    下载Driver Path: C:\Users\CHEN Tong\AppData\Roaming\JetBrains\IntelliJIdea2020.3\jdbc-drivers
    // 5. 选择SQL Server需要连接的Database > Schema 监测连接的DB > Test Connection

    /**
     * 将SQL Server Driver Jar添加到项目中：可以使用上一步下载的jar文件     ====> (添加jar)，或者直接通过maven来构建 !!
     * 在JDBC的上层加Hibernate                                        ====> (添加插件)，或者直接通过maven来构建 !!
     * 方式 1. Project Settings > Modules > Add Hibernate support
     * 方式 2. Right Click Project > Add Framework Support > Hibernate (注意: 使用最新的版本, 自定义下载，然后添加required中的jar文件)
     * Hibernate必须要有配置文件 > 配置在连接数据库时信息
     * 1. Add resources folder (Spring默认的项目框架自带有该目录)
     * 2. Add hibernate.cfg.xml 添加配置文件，添加时会自动的找到\resources文件夹, 然后添加...Spring Basic\src\main\resources\hibernate.cfg.xml
     */

    /**
     * 自动生成ORM匹配关系: Table.hbm.xml提供mapping信息   ===> 不再使用常规的JDBC对数据的操作(取消直接写SQL语句) !!
     * 1. 打开View > Persistence (持久层控制) > Generate Persistence Mapping by Database Schema 根据数据库的表来生成 !!
     * .  选择已经连接的数据库，特殊DB需要标明名称的前缀和后缀
     * .  定义生成到的package位置
     * .  定义生成到对应的Primitive Type, 选择Schema可以自定义映射的类型
     * .  勾选generate column & Separate XML
     * 2. 将生成出来的.xml文件剪切到 /resources下面 !! 同时将mapping信息添加到hibernate.cfg.xml文件中
     */
    public static void main(String[] args) {
        //  final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        //  try {
        //      SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        //      Session sessionAttribute = sessionFactory.openSession();
        //      testSessionQuery(sessionAttribute);
        //      sessionAttribute.close();
        //      sessionFactory.close();
        //  } catch (Exception e) {
        //      // The registry would be destroyed by the SessionFactory,
        //      // Destroy it manually when we have trouble building the SessionFactory
        //      StandardServiceRegistryBuilder.destroy(registry);
        //  }
    }

    // HQL: hibernate query language 一种Hibernate的语言, 一种类似于sql的简化的查询语言
    private static void testSessionQuery() {
        // Book: DB中的Schema表格
        // form Book: 提取Book表的所有的信息，并映射成指定类型的对象
        // Query<Book> query = sessionAttribute.createQuery("from Book", Book.class);
        // List<Book> books = query.getResultList();

        // sessionAttribute.beginTransaction();  ==> Transactions 是为了解决一系列SQL一起执行时带来的问题 !!
        // Book newBook = new Book();
        // newBook.setName("Test");
        // sessionAttribute.save(newBook); 存储新的对象到数据库表
        // sessionAttribute.getTransaction().commit();
    }
}
