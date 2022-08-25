package hibernate.framework.apis.mappings.one.to.many.bidirectional;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

// 1. 需要提供默认的构造器，否则在获取的时候会报错: No default constructor for entity
//    在从数据库中获取对象时，需要调用到对象的默认构造器
// 2. 无法直接调用重写的toString()方法 !!
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
