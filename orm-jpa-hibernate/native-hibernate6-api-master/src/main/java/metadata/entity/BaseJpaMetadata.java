package metadata.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // (name = "hibernate.metadata.metadata.entity.BaseJpaMetadata")
@Table(name = "t_jpa_metadata")
public class BaseJpaMetadata {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    protected String name;

    public BaseJpaMetadata() {
    }

    public BaseJpaMetadata(long id, String name) {
        this.id = id;
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
