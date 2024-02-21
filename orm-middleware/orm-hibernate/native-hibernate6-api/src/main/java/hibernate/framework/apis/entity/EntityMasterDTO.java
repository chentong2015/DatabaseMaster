package hibernate.framework.apis.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

// 主键同时作为其他Entity的外键(即是主键，同时也是外键，引用到其他表)
// The @PrimaryKeyJoinColumn annotation is used to specify that the primary key column of
// the currently annotated entity is also a foreign key to some other entity

// 实例：
// - The base class table in a JOINED inheritance strategy
// - The primary table in a secondary table mapping
// - The parent table in a @OneToOne relationship 基于主键一对一关系
@Entity(name = "hibernate.framework.apis.entity.EntityMasterDTO")
@Table(name = "t_entity_master_dto")
@PrimaryKeyJoinColumn(name = "m_master_id")
public class EntityMasterDTO {

    // TODO. 所有的Entity Class都应该设置@id主键id
}
