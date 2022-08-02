# 文件中自定义执行的Hql查询语句，取消sql的硬编码
# 程序中读取指定的resources stream, 生成hqlQuery
# 执行hql查询语句:
   - session.createQuery(hqlQuery)
   - entityManager.createQuery(qlString)
from com.hibernate5.testing.package1.MyEntity entity where entity.name ='test'