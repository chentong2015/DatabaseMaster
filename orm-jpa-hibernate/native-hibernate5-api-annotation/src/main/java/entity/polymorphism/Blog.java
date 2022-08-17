package entity.polymorphism;

import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// 该实体在通过interface查询时，不会被fetching
@Entity
@Table(name = "t_test_blog")
@Polymorphism(type = PolymorphismType.EXPLICIT)
public class Blog implements DomainModelEntity {

    @Id
    private int id;

    @Override
    public int getId() {
        return 0;
    }
}
