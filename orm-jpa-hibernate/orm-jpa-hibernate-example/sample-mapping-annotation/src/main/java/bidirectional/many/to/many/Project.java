package bidirectional.many.to.many;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "t_project")
public class Project {

    @Id
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "projects")
    private Set<Employee> employees;

    public Project() {
    }

    public Project(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
