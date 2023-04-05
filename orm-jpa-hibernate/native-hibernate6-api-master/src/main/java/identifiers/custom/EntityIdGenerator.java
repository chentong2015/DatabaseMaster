package identifiers.custom;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "t_base_entity")
public class EntityIdGenerator {

    // TODO. 将自定义的ID生成器作用到属性id字段上
    // Use custom generator one needs to configure its Hibernate' field entity to reference it
    // 1. 使用自定义的ID生成器策略
    // 2. 为ID生成器设置一个一致的名称
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
