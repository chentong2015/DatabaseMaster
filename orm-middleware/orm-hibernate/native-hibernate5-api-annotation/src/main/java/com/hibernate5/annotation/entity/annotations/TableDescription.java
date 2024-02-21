package com.hibernate5.annotation.entity.annotations;

// 自定义Annotation注解为Entity Table添加描述信息
public @interface TableDescription {

    // A short description of the JPA table (mandatory)
    String shortDescription();

    // A longer description of the JPA table (optional)
    String longDescription() default "";
}
