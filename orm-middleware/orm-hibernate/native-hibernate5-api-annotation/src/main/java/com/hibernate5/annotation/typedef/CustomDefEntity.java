package com.hibernate5.annotation.typedef;

import com.hibernate5.annotation.typedef.types.CharStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// 2. 使用@TypeDefs注入自定义的类型
@TypeDefs({
        @TypeDef(name = "char-string", typeClass = CharStringType.class)
})
@Entity
@Table(name = "t_my_typedef_entity")
public class CustomDefEntity {

    @Id
    @Column(name = "id")
    private long reference;

    // 使用自定义的类型，混合字段的类型，作用到字段上/列上
    @Column(name = "M_TRN_FMLY", nullable = false)
    @Type(type = "char-string")
    private String value;
}
