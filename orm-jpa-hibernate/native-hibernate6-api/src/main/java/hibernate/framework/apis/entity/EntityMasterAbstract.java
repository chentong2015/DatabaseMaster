package hibernate.framework.apis.entity;

import jakarta.persistence.*;

// TODO. 特别注意，Mapping映射的本质: 数据表的一行对应一个Java Class的实例对象
//  - 对于抽象类型来说，它没有实例对象，因此也不可能映射任何的数据表(或者数据表的一行)
//  - 如果是针对abstract super class抽象母类进行查询，将会得到作用继承类的数据
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class EntityMasterAbstract {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "name", length = 32)
    private String name;

    public EntityMasterAbstract() {
    }
}
