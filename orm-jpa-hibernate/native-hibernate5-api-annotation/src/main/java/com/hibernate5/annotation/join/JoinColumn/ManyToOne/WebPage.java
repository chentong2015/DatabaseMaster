package com.hibernate5.annotation.join.JoinColumn.ManyToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(name = "t_web_page")
public class WebPage {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "m_label")
    private String label;

    // 1. CascadeType     级联操作，存储ClosingEntity时会同时存储Type的数据
    // 2. "type_id"       用于join关联表的列，对应到关联表的主键
    // 3. @ForeignKey     定了两个列之间的外键约束的名称，可以不设置
    // 4. nullable        默认是不能为空，必须能找到对应的主键值
    // 5. NotFoundAction  设置在没有找到时执行的动作
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id", foreignKey = @ForeignKey(name = "entity_type_pk"), nullable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Type type;

    public WebPage() {
    }

    public WebPage(String label) {
        this.label = label;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
