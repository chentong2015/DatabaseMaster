package jpa.api.named.query;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "t_named_table")

// TODO. 直接在entity class类型上声明query查询语句，通过name名称来直接调用
// @NamedQueries({@NamedQuery(name = "EntityNamed.findAll", query = "SELECT en FROM EntityNamedQuery en"),
//         @NamedQuery(name = "EntityNamed.findByUrl", query = "SELECT en FROM EntityNamedQuery en WHERE en.url = :url")})
@NamedQuery(name = "EntityNamed.findAll", query = "SELECT en FROM EntityNamedQuery en")
@NamedQuery(name = "EntityNamed.findByUrl", query = "SELECT en FROM EntityNamedQuery en WHERE en.url = :url")
public class EntityNamedQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "URL")
    private String url;

    @Column(name = "name")
    private String name;
}
