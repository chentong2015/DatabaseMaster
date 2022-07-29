package hibernate.framework.apis.entity;

/**
 * JavaDoc for EntityMeta Class
 *
 * @author Tong
 */
public class EntityMeta {

    public long id;
    public String name;

    public EntityMeta(String name) {
        this.name = name;
    }

    public EntityMeta() {
    }

    public long getId() {
        return this.id;
    }

    protected void setId(long id) {
        this.id = id;
    }

    /**
     * The name of the person
     */
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
