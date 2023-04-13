package entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "t_user")
public class User {

    // The sequence_name must be a real sequence declared in your Oracle Database
    // The increment_size must be equal to the "incrementBy" of your sequence.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_gen")
    @GenericGenerator(
            name = "seq_gen",
            strategy = "id_generator.SequenceIdGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "NAME_SEQUENCE"),
                    @Parameter(name = "increment_size", value = "10")}
    )
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;
}
