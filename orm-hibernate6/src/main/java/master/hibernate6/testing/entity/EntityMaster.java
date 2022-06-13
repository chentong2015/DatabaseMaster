package master.hibernate6.testing.entity;

import java.util.Date;

// TODO. 在Entity Class上可以不使用任何Hibernate的注解
//  只在hibernate mapping xml文件中配置相关的映射信息到database中的table行
public class EntityMaster {

    private Long id;
    private String label;
    private Date date;
    private Integer version;
    private String description;
    private Long code;
    // private ProxySetType proxySetType; 自定义的类型

    // 做为mapping的entity，必须提供Getter和Setter
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }
}
