package hibernate.framework.apis.mappings.many.to.one.model;

import java.io.Serializable;

public class Grade implements Serializable {

    private int id;
    protected String name;

    public Grade() {
    }

    public Grade(String name) {
        this.name = name;
    }

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
