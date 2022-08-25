package hibernate.framework.apis.mappings.many.to.one;

public class StudentAddress {

    private long id;
    private String name;

    public StudentAddress() {
    }

    public StudentAddress(String name) {
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
