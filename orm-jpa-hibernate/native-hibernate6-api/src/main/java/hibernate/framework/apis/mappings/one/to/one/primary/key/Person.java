package hibernate.framework.apis.mappings.one.to.one.primary.key;

import java.io.Serializable;

public class Person implements Serializable {

    // 基于主键的单向One-to-One在xml文件进行配置
    private int id;
    private String name;
    private int age;
    private IdCard idCard;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public IdCard getIdCard() {
        return idCard;
    }

    public void setIdCard(IdCard idCard) {
        this.idCard = idCard;
    }
}
