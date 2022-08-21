package hibernate.framework.apis.associations.unidirectional.one.to.many;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Grade implements Serializable {

    private int id;
    private String name;
    private Set<Student> students = new HashSet<>();

    public Grade() {
    }

    public Grade(String name) {
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

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
