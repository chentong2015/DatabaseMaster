package id.geneator;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.SEQUENCE;

// TODO. @GeneratedValue 提供主键值的特殊生成策略
// GenerationType.AUTO      默认配置的自动增加 > SQLite, H2 Database
// GenerationType.IDENTITY  部分数据库提供支持 > MySQL, PSQL (从0开始依次增加)
// GenerationType.SEQUENCE  通过序列来生成ID  > Oracle, PSQL
// GenerationType.TABLE     容器指定用底层的数据表确保唯一
public class IdGeneratorType {

    // TODO. @GeneratedValue和@GenericGenerator协同使用，指定id的生成策略
    private long id;

    // 1. @GeneratedValue indicates generator to use on your attribute by referencing the name of the generator to use.
    // 2. @GenericGenerator Use Hibernate’s increment generation strategy for this entity’s identifier values
    @Id
    @GeneratedValue(generator = "increment") // 通过名称来指定使用什么生成器，名称保持一致，名称不会在DB中存储
    @GenericGenerator(name = "increment", strategy = "increment") // 定义生成器的名称和特殊的策略
    public Long getId() {
        return id;
    }

    // @GenericGenerator(name = "native", strategy = "native") 随机自动地增加

    // 不建议使用UUID做主键
    // https://www.baeldung.com/jpa-strategies-when-set-primary-key
    @Id
    @GeneratedValue(generator = "system-id")
    @GenericGenerator(name = "system-id", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private String id2 = null;


    // 使用定义好的策略生成类型
    private long id3;

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "CUST_SEQ")
    @Column(name = "CUST_ID")
    public Long getId3() {
        return id3;
    }
}
