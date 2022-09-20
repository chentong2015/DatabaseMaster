package caching.query_cache;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_caching_query")
public class QueryCacheEntity {

    @Id
    private int id;

    @Column(name = "name")
    private String name;

    public QueryCacheEntity() {
    }

    public QueryCacheEntity(int id, String name) {
        this.id = id;
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

    @Override
    public String toString() {
        return "QueryCacheEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
