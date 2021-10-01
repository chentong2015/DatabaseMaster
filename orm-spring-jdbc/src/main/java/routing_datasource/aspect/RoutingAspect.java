package routing_datasource.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import routing_datasource.MultiDataSourceHolder;
import routing_datasource.annotation.Router;

import java.lang.reflect.Method;

// 使用AOP让Spring识别自定义的注解
@Aspect
public class RoutingAspect {

    // 切点切自定义的注解
    @Pointcut("@annotation(routing_datasource.annotation.Router)")
    private void pointCut() {
    }

    @Before(value = "pointCut()")
    public void methodBefore(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        // 从切点的注解上拿到指定的属性值
        Router router = method.getAnnotation(Router.class);
        String routingField = router.routingField();
        // 拿到目标方法的参数
        Object[] args = joinPoint.getArgs();
        // 判断传入的是否有路由字段
        // calculate mapping index, get target data source
        // set data source to thread local
    }

    // 在拦截请求(Controller中的方法)之后，需要清除保存到Thread Local中的数据
    @After("pointCut()")
    public void removeAfter() {
        MultiDataSourceHolder.clearDataSourceKey();
        MultiDataSourceHolder.clearTableIndex();
    }
}
