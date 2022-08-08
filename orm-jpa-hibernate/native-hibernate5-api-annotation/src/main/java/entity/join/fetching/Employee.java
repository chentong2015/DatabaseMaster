package entity.join.fetching;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity(name = "entity.join.fetching.Employee")
@Table(name = "t_employee_1")
public class Employee {

    @Id
    private Long id;

    // 这个字段也可以作为唯一性的id来查询
    @NaturalId
    private String username;

    // TODO. 多对一，这里对应到table中的字段是department_id
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    public Employee() {
    }

    public Employee(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }
}
