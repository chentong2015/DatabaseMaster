package entity.join.many.to.one;

import javax.persistence.*;

@Entity // 与数据库 对应生成的能力
@Table(name = "t_type_1") // 表名称
public class Type {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;

    public Type() {
    }

    public Type(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
