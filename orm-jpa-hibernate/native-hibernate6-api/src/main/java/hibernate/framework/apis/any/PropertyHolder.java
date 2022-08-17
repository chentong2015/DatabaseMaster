package hibernate.framework.apis.any;

import hibernate.framework.apis.any.model.IntegerProperty;
import hibernate.framework.apis.any.model.MyProperty;
import hibernate.framework.apis.any.model.StringProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyDiscriminator;
import org.hibernate.annotations.AnyDiscriminatorValue;
import org.hibernate.annotations.AnyKeyJavaClass;

@Entity
@Table(name = "t_property_holder")
public class PropertyHolder {

    @Id
    private Long id;

    // Hibernate V6版本写法(区别之前的版本) => 完全对等hbm.xml文件中的配置
    @Any
    @AnyDiscriminator(DiscriminatorType.STRING)
    @AnyDiscriminatorValue(discriminator = "S", entity = StringProperty.class)
    @AnyDiscriminatorValue(discriminator = "I", entity = IntegerProperty.class)
    @Column(name = "property_type")
    @AnyKeyJavaClass(Long.class)
    @JoinColumn(name = "property_id") // 用来关联表查询的列名称
    @SuppressWarnings("JpaAttributeTypeInspection") // 去掉提示'Basic' attribute type should not be 'MyProperty'
    private MyProperty property;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MyProperty getProperty() {
        return property;
    }

    public void setProperty(MyProperty property) {
        this.property = property;
    }
}
