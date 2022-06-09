package master.hibernate6.testing.package2;

import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
@Table(name = "t_second")
public class MyPojo {
    
    @Id
    public String uid;
}
