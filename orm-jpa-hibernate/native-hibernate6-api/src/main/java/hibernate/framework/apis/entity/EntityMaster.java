package hibernate.framework.apis.entity;

import java.util.Date;

// 做为mapping的entity，必须提供Getter和Setter
public class EntityMaster {

    private Long id;
    private String label;
    private Date date;
    private Integer version;
    private Long code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }
}
