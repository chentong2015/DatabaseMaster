package master.hibernate.testing.first;

import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
@Table(name = "t_first")
public class MyPojo {
    
    @Id
    public String id;
}
