package jpa.annotations.entity.typedef;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// 可以定义多个类型的名称，在Entity Class中使用
@TypeDefs({
        @TypeDef(name = "char-string", typeClass = CharStringType.class)
})
@Entity
@Table(name = "t_my_typedef_entity")
public class MyTypeDefEntity {

    @Id
    @Column(name = "id")
    private long reference;

    @Column(name = "M_TRN_FMLY", nullable = false)
    @Type(type = "char-string")
    private String value;
}
