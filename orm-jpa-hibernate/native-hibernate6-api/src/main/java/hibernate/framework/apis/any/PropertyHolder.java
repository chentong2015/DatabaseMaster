package hibernate.framework.apis.any;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "property_holder")
public class PropertyHolder {

    @Id
    private Long id;

    // Hibernate V6版本的写法(区别之前的版本)，完全对等hbm.xml文件中的配置
    // @Any
    // @AnyDiscriminator(DiscriminatorType.STRING)
    // @AnyDiscriminatorValue(discriminator = "S", entity = StringProperty.class)
    // @AnyDiscriminatorValue(discriminator = "I", entity = IntegerProperty.class)
    // @AnyKeyJavaClass(Long.class)
    // @Column(name = "property_type")
    // @JoinColumn(name = "property_id")
    // private Property property;

}
