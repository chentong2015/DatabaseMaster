package com.hibernate6.main.id.generator;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "t_base_entity")
public class BaseIdGenerator {

    // TODO. 使用自定义的ID主键的生成规则，来自hibernate的扩展
    // UID1: Typically references your module name
    // UID2: Typically references your table
    // COUNT (optional): Reserves a number of IDs as specified by the COUNT attribute, by default set to 1.
    @GeneratedValue(generator = "personIdGenerator")
    @GenericGenerator(
            name = "personIdGenerator",
            strategy = "com.hibernate.main.id.generator.MyStoredTableIdGenerator",
            parameters = {
                    @Parameter(name = "UID1", value = "STATICS"),
                    @Parameter(name = "UID2", value = "CATEGORY")
            }
    )
    @Id
    @Column(name = "ID")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
