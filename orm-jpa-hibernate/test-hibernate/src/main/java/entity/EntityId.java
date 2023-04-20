package entity;

public class EntityId {

    private long id;
    private String name;

    public EntityId() {
    }

    public EntityId(long id, String name) {
        this.id = id;
        this.name = name;
    }

    // TODO. Entity Getter方法不能设置成Final
    public final long getId() {
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
