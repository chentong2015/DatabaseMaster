package com.hibernate5.testing.many_to_one_master;

import com.hibernate5.testing.HibernateSessionUtil;
import org.hibernate.Session;

import java.util.List;

// 搭建Many-to-one测试案例
// 在数据中创建TableA和TableB多对一关系，将外键的关联关系删除之后再添加0.0的错误id
// 1. 未添加not-found="ignore"
//    v5.3 查全表时对于异常的id会抛出异常
//    v5.6 查全表时不会自动检查异常的外键id
// 2. 添加not-found="ignore"
//    新旧版本都无异常抛出，只返回能够查询到的数据
//    新版本在获取属性值的时候才会抛出异常 !!
public class ManyToOneMaster {

    public static void main(String[] args) {
        try {
            Session session = HibernateSessionUtil.getSession();
            String hql = "FROM " + CommodityContract.class.getName();
            List<CommodityContract> contracts = session.createQuery(hql, CommodityContract.class).getResultList();
            System.out.println(contracts.size());

            for (CommodityContract contract : contracts) {
                if (contract.getAsset() != null) {
                    System.out.println(contract.getAsset().getLabel());
                }
            }
            HibernateSessionUtil.closeSession();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
