package annotation;

import jakarta.annotation.Resource;

import javax.sql.DataSource;
import java.util.Set;

// https://docs.oracle.com/javaee/6/tutorial/doc/bncjk.html
public class ResourceInjection {

    // 根据完整路径来获取该资源 annotation.ResourceInjection/myDB
    @Resource
    private DataSource myDB;

    // 根据自定义的名称来获取资源并完成DI注入
    @Resource(name = "customerSet")
    private Set<String> sets;
}
