package identifiers.custom;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "t_base_entity")
public class EntityIdGenerator {

    // TODO. 将自定义的ID生成器作用到属性id字段上
    // Use custom generator one needs to configure its Hibernate' field entity to reference it
    // 使用的generator的名称必须和自定义的生成器的名称一致，否则会出现如下错误
    // org.hibernate.engine.jdbc.spi.SqlExceptionHelper: sequence does not exist
    @Id
    @GeneratedValue(generator = "personIdGenerator")
    @GenericGenerator(name = "personIdGenerator",
            strategy = "com.hibernate.main.id.generator.MyStoredTableIdGenerator",
            parameters = {@Parameter(name = "UID1", value = "STATICS"),
                    @Parameter(name = "UID2", value = "CATEGORY")}
    )
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
