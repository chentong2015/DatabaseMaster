package mybatis.spring;

import mybatis.spring.base.MyDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"mybatis.spring"})
@EnableTransactionManagement
@MapperScan(basePackages = {"mybatis.spring.dao"})
public class MyBatisConfig {

    // 为什么Spring整合Mybatis之后，一级缓存会失效 ?

    // @MapperScan注解的作用和@Bean(mapperScannerConfigurer)功能一致 ?
    // TODO: 注解最终的效果是导入bean定义 > MapperScannerConfigurer定义
    // 使用@Import({MapperScannerRegistrar.class})
    // 使用@Import第二种方式 MapperScannerRegistrar implements ImportBeanDefinitionRegistrar

    // TODO: 最终扫描的Mapper Interface会被设置beanClass: MapperFactoryBean
    // ClassPathMapperScanner.processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
    //    1. 指定bean definition调用的构造函数，并传递参数值，调用有参构造器
    //       需要传递原生接口类型做代理
    //    definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName);
    //    ...
    //    2. 改变bean定义的信息, 因为Mapper Interface是能被实例化的
    //       任何的mapper在被实例化的时候都调用MapperFactoryBean的构造器 !!
    //    definition.setBeanClass(this.mapperFactoryBeanClass);
    //    ...
    //    3. 修改注入模型，在实例化时，给指定的字段赋值, 完成注入
    //       AUTOWIRE_BY_TYPE : (容错处理)只要容器中有这个类型的组件，则可以完成注入
    //       AUTOWIRE_BY_NAME : 容器中存储的名称必须完全一致
    //    definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE); 注入模型
    // }

    // TODO: 最终生成代理对象的方法 ==> MyBatis源码的实现：MapperProxy<T>类型 !!
    // MapperFactoryBean<T>
    // @Override
    // public T getObject() throws Exception {
    //    return getSqlSession().getMapper(this.mapperInterface);
    // }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setConfigLocation(new ClassPathResource("mybatis/mybatis-main.config.xml"));
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResource("mybatis/*.xml"));
        return factoryBean;
    }

    @Bean
    public DataSource dataSource() {
        MyDataSource dataSource = new MyDataSource();
        // set data source info
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSource dataSource = dataSource();
        // PlatformTransactionManager transactionManager = new PlatformTransactionManager(dataSource());
        return null;
    }
}
