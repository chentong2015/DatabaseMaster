package entity.any;

import entity.any.model.IntegerProperty;
import entity.any.model.MyProperty;
import entity.any.model.StringProperty;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import javax.persistence.*;

@Entity
@Table(name = "t_property_holder")
public class PropertyHolder {

    @Id
    private Long id;

    // @AnyMetaDef 确定子类区别类型的value值的不同
    // @JoinColumn 用于join关联表查询的列(关联表的主键值)
    @Any(metaColumn = @Column(name = "property_type"))
    @AnyMetaDef(idType = "long", metaType = "string", metaValues = {
            @MetaValue(targetEntity = IntegerProperty.class, value = "I"),
            @MetaValue(targetEntity = StringProperty.class, value = "S")})
    @JoinColumn(name = "property_id")
    @SuppressWarnings("JpaAttributeTypeInspection")
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
