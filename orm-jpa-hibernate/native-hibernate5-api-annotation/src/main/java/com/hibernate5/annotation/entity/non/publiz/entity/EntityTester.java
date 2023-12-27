package com.hibernate5.annotation.entity.non.publiz.entity;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

// 在和entity class同一个package下测试
public class EntityTester {

    // 测试不同的Entity注解的标注方式，也能查询到数据
    public static void testGetSampleData(Session session) {
        Query<Sample> query = session.createQuery("FROM " + Sample.class.getName(), Sample.class);
        List<Sample> samples = query.getResultList();
        for (Sample sample : samples) {
            System.out.println(sample);
        }
        session.close();
    }
}
