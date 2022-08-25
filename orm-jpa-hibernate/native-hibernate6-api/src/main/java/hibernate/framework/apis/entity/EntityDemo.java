package hibernate.framework.apis.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

// TODO. 注解@Entity指明这个类是遵循orm映射的类, 一个实体Bean, 每个实例代表数据表中的一行数据
//       注解@Table指定Entity所要映射带数据库表, 默认采用"类名"作为映射表的表名
// 1. Entity(name)名称默认取的是class类型的名称, 不能是Java Persistence查询中的保留文字
// 2. Entity(name)名称用于在HQL查询中对entity引用, 如果有两个相同的名称将会造成冲突
// 3. 配置.hbm.xml可以将同一个Entity映射到数据库中不同的表
@Entity(name = "t_demo_table")
@Table(name = "t_demo_table",
        schema = "public", // 使用特定的schema
        uniqueConstraints = @UniqueConstraint(columnNames = {"source_id"})) // 约定具体列的唯一性, 也可以直接在@Column定义上约束
public class EntityDemo {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;

    // 等效于hbm.xml中对于属性的<column>配置, 可以约定默认值(存在适配的问题)
    @Column(name = "name", columnDefinition = "character varying")
    private String name;

    // 可以定义类的非空性和unique唯一性
    @Column(name = "source_id", nullable = false)
    private String sourceId;

    @Column(name = "my_edit")
    private Boolean myEdit;

    // TODO. 使用Version来实现乐观锁
    @Version
    private Integer version;

    // 必须提供Getter&Setter方法，满足在HBM mapping文件中的属性配置
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
