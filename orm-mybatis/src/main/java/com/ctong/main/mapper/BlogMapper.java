package com.ctong.main.mapper;

import com.ctong.main.model.Blog;
import com.ctong.main.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

// 映射器是一些绑定映射语句的接口: 使用Java注解将"实体类型"和"SQL语句"进行映射
public interface BlogMapper {

    //  #{} 参数语法时, MyBatis会创建PreparedStatement参数占位符 !!
    // 对于此注解中复杂的SQL语句，最好使用XML来完成映射
    @Select("SELECT * FROM blog WHERE id = #{id}")
    Blog selectBlog(int id);

    // TODO: SQL语句中使用#和$的区别
    // ${columnName} MyBatis不会修改或转义该字符串, ${column}会被直接(动态)替换
    // #{value}会使用?预处理, #{} equivalent to PreparedStatement in JDBC
    @Select("select * from user where ${column} = #{value}")
    User findByColumn(@Param("column") String column, @Param("value") String value);

    // @Param("id") 通过参数的名称来替换，可以忽略"替换字符串"所在的位置
    // @Result 声明返回结果值的类型
    @Update("Update user set name = #{name} where id = #{id}")
    @Result(javaType = int.class)
    int updateUsernameById(@Param("id") int id, @Param("name") String name);

    // 多表结合merge, 合并整合最后的信息
    // 将返回的结果数据隐射到类型实例(对应指定的类型属性)
    // @One : specify the nested statement for retrieving single object 定义嵌套的声明语句，用于获取当个对象
    // @Many: specify the nested statement for retrieving collections 获取集合属性的值
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "id", one = @One(select = "selectUserEmailById", fetchType = FetchType.LAZY)),
            @Result(property = "telephoneNumbers", column = "id", many = @Many(select = "selectAllUserTelephoneNumberById", fetchType = FetchType.LAZY))
    })
    @Select("SELECT id, name FROM users WHERE id = #{id}")
    User selectById(int id);
}
