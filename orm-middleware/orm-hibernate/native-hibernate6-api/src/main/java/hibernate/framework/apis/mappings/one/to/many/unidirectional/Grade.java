package hibernate.framework.apis.mappings.one.to.many.unidirectional;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Grade implements Serializable {

    private int id;
    private String name;
    private Set<Student> students = new HashSet<>();
    private Set<Long> typeReferences;

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

    public Set<Long> getTypeReferences() {
        return typeReferences;
    }

    public void setTypeReferences(Set<Long> typeReferences) {
        this.typeReferences = typeReferences;
    }
}
