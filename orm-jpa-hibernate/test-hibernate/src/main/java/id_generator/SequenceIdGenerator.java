package id_generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import java.io.Serializable;

/*
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO, generator = "input_seq_gen")
  @GenericGenerator(
     name = "input_seq_gen",
     strategy = "id_generator.SequenceIdGenerator",
     parameters = {
        @Parameter(name = "sequence_name", value = "RBPL_INPUT_SEQUENCE"),
        @Parameter(name = "increment_size", value = "100")}
  )
  @Column(name = "COLLATERAL_RUN_ID")
  private Long id;
 */
public class SequenceIdGenerator implements IdentifierGenerator {

    // TODO. 这里将SequenceStyleGenerator作为私有属性，相当于重写hibernate SequenceStyleGenerator
    // 由于不同DB的ID主键生成策略有所不同，在应用时需要区别考虑
    // We need a SequenceStyleGenerator in Oracle but native identity generator on Sybase.
    private SequenceStyleGenerator strategy;

    // @Override
    // public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
    //     strategy = new SequenceStyleGenerator();
    //     strategy.configure(type, params, serviceRegistry);
    // }

    // @Override
    // public void registerExportables(Database database) {
    //     // IdentifierGenerator.super.registerExportables(database);
    //     strategy.registerExportables(database);
    // }

    // @Override
    // public void initialize(SqlStringGenerationContext context) {
    //     // IdentifierGenerator.super.initialize(context);
    //     strategy.initialize(context);
    // }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return strategy.generate(session, object);
    }
}
