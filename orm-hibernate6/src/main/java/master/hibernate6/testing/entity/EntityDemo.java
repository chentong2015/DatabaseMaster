package master.hibernate6.testing.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

// TODO. 注解@Entity指明这个类是遵循orm映射的类, 一个实体Bean, 每个实例代表数据表中的一行数据
//       注解@Table指定Entity所要映射带数据库表, 如果缺少, 则系统默认采用类名作为映射表的表名
//
// 1. Entity(name)名称默认取的是class类型的名称, 不能是Java Persistence查询中的保留文字
// 2. 自定义Entity实体的名称, 该名称用于在HQL查询中对entity的引用(特定名称)
// 3. 可以使用Entity(name)在操作同一个Entity时对应到不同的表 ==> 配置在.hbm.xml文件
@Entity(name = "t_demo_table")
@Table(name = "t_demo_table", schema = "public")
public class EntityDemo {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Object id;

    @Column(name = "name")
    private String name;

    // TODO: 使用Version来实现乐观锁
    @Version
    private Integer version;

    // TODO. 必须提供Getter&Setter方法，满足在HBM mapping文件中的属性配置
    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
