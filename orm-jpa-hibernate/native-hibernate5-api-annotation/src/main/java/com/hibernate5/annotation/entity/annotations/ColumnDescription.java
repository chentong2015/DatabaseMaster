package com.hibernate5.annotation.entity.annotations;

// 这个注解必须配合@Column(name ="")注解使用
public @interface ColumnDescription {

    // A short description of the JPA column (mandatory)
    String shortDescription();

    // A longer description of the JPA column (optional)
    String longDescription() default "";
}
