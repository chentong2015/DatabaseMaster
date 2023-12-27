package com.hibernate5.annotation.entity.immutability;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// @Immutable不可变实体，可标记在entities或者collections
// 1. Hibernate is going to perform several optimizations 减少内存占用，不需要执行dirty checking
// 2. When loading the entity and trying to change its state, Hibernate will skip any modification
@Entity
@Table(name = "t_entity_immutability")
@Immutable
public class EntityImmutability {

    @Id
    private Long id;

    @Column(name = "message")
    private String message;
}
