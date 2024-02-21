package com.mybatis.basic.testJavaAnnotation.mapper;

import com.mybatis.basic.model.Blog;
import com.mybatis.basic.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

// 映射器是一些绑定映射语句的接口: 使用Java注解将"实体类型"和"SQL语句"进行映射
public interface BlogMapper {

    @Select("SELECT * FROM blog WHERE id = #{id}")
    Blog selectBlog(int id);

    @Select("select * from t_user where ${column} = #{value}")
    User findUserByColumn(@Param("column") String columnName, @Param("value") String columnValue);

    // @Param("id") 通过参数的名称来替换，可以忽略"替换字符串"所在的位置
    @Update("Update t_user set name = #{name} where id = #{id}")
    @Result(javaType = int.class)
    int updateUsernameById(@Param("id") int id, @Param("name") String name);

    // 多表结合merge, 整合结果信息: 将返回的结果数据隐射到类型实例(对应指定的类型属性)
    // @One : specify the nested statement for retrieving single object
    // @Many: specify the nested statement for retrieving collections
    @Select("SELECT id, name FROM t_user WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "id", one = @One(select = "selectUserEmailById", fetchType = FetchType.LAZY)),
            @Result(property = "telephoneNumbers", column = "id", many = @Many(select = "selectAllUserTelephoneNumberById", fetchType = FetchType.LAZY))})
    User selectById(int id);
}
