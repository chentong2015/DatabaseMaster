package hibernate.framework.apis.datamodel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "t_book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    // TODO. 等效于hbm.xml中对于属性的<column>配置, 可以约定默认值(存在适配的问题)
    @Column(name = "name", columnDefinition = "character varying")
    private String name;

    @Column(name = "title", columnDefinition = "varchar(255) default 'java'")
    private String title;

    public Book() {
    }

    public Book(long id, String name, String title) {
        this.id = id;
        this.name = name;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
