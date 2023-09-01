package hibernate.framework.apis.mappings.many.to.one.model;

import java.io.Serializable;

public class Student implements Serializable {

    private int id;
    private String name;
    private int age;
    private Grade grade;

    // 额外添加的测试字段
    private hibernate.framework.apis.mappings.many.to.one.package1.Grade grade1;
    private StudentAddress address;

    public Student() {
    }

    public Student(String name, int age, Grade grade) {
        this.name = name;
        this.age = age;
        this.grade = grade;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public hibernate.framework.apis.mappings.many.to.one.package1.Grade getGrade1() {
        return grade1;
    }

    public void setGrade1(hibernate.framework.apis.mappings.many.to.one.package1.Grade grade1) {
        this.grade1 = grade1;
    }

    public StudentAddress getAddress() {
        return address;
    }

    public void setAddress(StudentAddress address) {
        this.address = address;
    }
}
