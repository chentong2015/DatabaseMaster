package com.hibernate5.testing.many_to_one_master;

import com.hibernate5.testing.HibernateSessionUtil;
import org.hibernate.Session;

import java.util.List;

// 搭建Many-to-one测试案例: 将外键的关联关系删除之后再添加0.0的错误id
// 1. 未添加not-found="ignore"
//    v5.3 查全表时不会自动检查异常的外键id
//    v5.6 查全表时对于异常的id会抛出异常
// 2. 添加not-found="ignore"
//    新旧版本都无异常抛出，只是将异常的错误进行忽略，id=0的主键找不到数据的问题始终存在
//    返回能够查询到的所有数据，只有在获取属性值时才会抛出异常 !!
public class ManyToOneMaster {

    public static void main(String[] args) {
        try {
            Session session = HibernateSessionUtil.getSession();
            String hql = "FROM " + CommodityContract.class.getName();
            List<CommodityContract> contracts = session.createQuery(hql, CommodityContract.class).getResultList();
            System.out.println(contracts.size());

            for (CommodityContract contract : contracts) {
                System.out.println(contract.getLabel());
                // System.out.println(contract.getAsset().getLabel());
                // if (contract.getAsset() != null) {
                // }
            }
            HibernateSessionUtil.closeSession();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
