package entity.join.many.to.one;

import javax.persistence.*;

@Entity
@Table(name = "t_web_page")
public class WebPage {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "m_label")
    private String label;

    // TODO. 如果设置CascadeType级联操作，则在存储ClosingEntity时会同时存储Type的数据
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id", foreignKey = @ForeignKey(name = "entity_type_pk"))
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
