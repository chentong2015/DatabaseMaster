package caching.second.level;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

// 设置使用的缓存策略，以及要使用的二级缓存Region区
@Entity
@Table(name = "t_cache_second_entity")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "second_entity_cache")
public class SecondCacheEntity {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "salary")
    private double salary;

    public SecondCacheEntity() {
    }

    public SecondCacheEntity(long id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
