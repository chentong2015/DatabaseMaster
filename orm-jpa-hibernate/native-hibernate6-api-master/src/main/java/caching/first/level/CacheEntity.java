package caching.first.level;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_cache_entity")
public class CacheEntity {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    protected String name;

    public CacheEntity() {
    }

    public CacheEntity(long id, String name) {
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

    @Override
    public String toString() {
        return "CacheEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
