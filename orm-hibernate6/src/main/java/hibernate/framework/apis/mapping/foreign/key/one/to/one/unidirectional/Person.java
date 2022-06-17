package hibernate.framework.apis.mapping.foreign.key.one.to.one.unidirectional;

import java.io.Serializable;

public class Person implements Serializable {

    private int id;
    private String name;
    private int age;
    // One-to-One的外键关系
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
