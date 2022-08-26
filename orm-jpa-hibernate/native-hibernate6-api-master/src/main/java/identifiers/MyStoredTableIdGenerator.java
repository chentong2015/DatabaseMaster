package identifiers;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

// 自定义ID的生成器，根据不同的策略实现generate()方法
// - UuidGenerator
// - IncrementGenerator
// - IncrementGenerator
// - ...
public class MyStoredTableIdGenerator implements IdentifierGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor implementor, Object object) throws HibernateException {
        return null;
    }
}
