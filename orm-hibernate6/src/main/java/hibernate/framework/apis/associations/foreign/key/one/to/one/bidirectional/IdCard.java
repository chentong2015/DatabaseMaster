package hibernate.framework.apis.associations.foreign.key.one.to.one.bidirectional;

import java.io.Serializable;

public class IdCard implements Serializable {

    private int id;
    private String code;
    // 补充双向One-to-One关系
    private Person person;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
