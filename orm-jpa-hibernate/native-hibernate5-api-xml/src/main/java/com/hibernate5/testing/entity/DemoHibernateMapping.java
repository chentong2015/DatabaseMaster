package com.hibernate5.testing.entity;

import com.hibernate5.testing.session.HibernateSessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.Set;

public class DemoHibernateMapping {

    public static void main(String[] args) {
        testSaveData();
        // testGetDataByNaturalId();
    }

    private static void testSaveData() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateSessionUtil.getSession();
            transaction = session.beginTransaction();

            Set<Fee> feeSet = new HashSet<>();
            Fee fee1 = new Fee("fee 01", 10.0);
            Fee fee2 = new Fee("fee 02", 20.0);
            feeSet.add(fee1);
            feeSet.add(fee2);
            Fees fees1 = new Fees("new currency 01", feeSet);
            Event event = new Event(2.0, 2, "2.0", fees1);
            session.persist(event);

            transaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            transaction.rollback();
        } finally {
            HibernateSessionUtil.closeSession();
        }
    }

    private static void testGetDataByNaturalId() {
        Session session = null;
        try {
            session = HibernateSessionUtil.getSession();
            Event event = session.byNaturalId(Event.class)
                    .using("underlyingId", 1.0)
                    .using("underlyingVersion", 1)
                    .load();
            System.out.println(event);
        } finally {
            HibernateSessionUtil.closeSession();
        }
    }
}
