package com.hibernate5.testing.many_to_one_master;

import com.hibernate5.testing.HibernateSessionUtil;
import org.hibernate.Session;

import java.util.List;

// 搭建Many-to-one测试案例
// 在数据中创建TableA和TableB多对一关系，将外键的关联关系删除之后再添加0.0的错误id
// 通过配置not-found="ignore"来忽略id找不到的异常，只返回能够查询到的数据
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
