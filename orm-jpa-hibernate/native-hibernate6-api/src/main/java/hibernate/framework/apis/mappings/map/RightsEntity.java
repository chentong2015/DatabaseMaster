package hibernate.framework.apis.mappings.map;

import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name = "t_map_rights")
public class RightsEntity {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "group")
    private String group;

    // 创建额外的Map Collection的Table数据表 ??
    @ElementCollection(targetClass = PermissionsEntity.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "t_map_rights_permissions", joinColumns = @JoinColumn(name = "right_id"))
    @MapKeyEnumerated(EnumType.ORDINAL) // 设置键值的类型，取枚举类型定义的ORDINAL序数
    @MapKeyColumn(name = "type")        // 设置key键值对应的列
    private Map<RightsType, PermissionsEntity> permissionsMap;

    public RightsEntity() {
    }

    public RightsEntity(int id, String group) {
        this.id = id;
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Map<RightsType, PermissionsEntity> getTemplatePermissions() {
        return permissionsMap;
    }

    public void setPermissionsMap(Map<RightsType, PermissionsEntity> permissionsMap) {
        this.permissionsMap = permissionsMap;
    }
}
