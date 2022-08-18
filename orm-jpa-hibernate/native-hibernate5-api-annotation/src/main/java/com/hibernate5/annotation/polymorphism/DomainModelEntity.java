package com.hibernate5.annotation.polymorphism;

// .createQuery("select e from com.hibernate5.annotation.polymorphism.DomainModelEntity e").getResultList();
//
// TODO. 当在查询接口时，Hibernate只会fetch以下两种entites的数据
// 1. 没有标记PolymorphismType的实体，默认是IMPLICIT类型
// 2. 显式标记了@Polymorphism(type = PolymorphismType.IMPLICIT)的实体
public interface DomainModelEntity {

    int getId();
}
