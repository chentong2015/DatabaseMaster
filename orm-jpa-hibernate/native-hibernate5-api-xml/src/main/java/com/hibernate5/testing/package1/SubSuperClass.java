package com.hibernate5.testing.package1;

import com.hibernate5.testing.inheritance.SuperClass;

public class SubSuperClass extends SuperClass {

    private String subName1;

    public String getSubName1() {
        return subName1;
    }

    public void setSubName1(String subName1) {
        this.subName1 = subName1;
    }
}
