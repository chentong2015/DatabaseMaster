package master.hibernate.testing.second;

import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
@Table(name = "t_second")
public class MyPojo {
    
    @Id
    public String uid;
}
