package hibernate.framework.apis.mappings.map;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PermissionsEntity {

    @Column(name = "permission")
    private String permission;

    public PermissionsEntity() {
    }

    public PermissionsEntity(String permission) {
        this.permission = permission;
    }
}
