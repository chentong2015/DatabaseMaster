package com.mybatis.basic.config.plugins;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// TODO: 插件代理的实现
// target被代理对象上调用的方法，会执行到代理类Plugin上的invoke()方法
// 如果满足method拦截的条件，则会调用自定义拦截器的拦截.intercept()方法
public class Plugin implements InvocationHandler {

    // 需要被代理的实例: executor执行器
    private final Object target;
    // 拦截器实例: 自定义的拦截器
    private final Interceptor interceptor;
    // 拦截器需要拦截的方法摘要
    // Class键为Executor等上述的四个，值为需要被拦截的方法
    private final Map<Class<?>, Set<Method>> signatureMap;

    // 此类不能直接创建，需要通过静态方法wrap来创建代理类
    private Plugin(Object target, Interceptor interceptor, Map<Class<?>, Set<Method>> signatureMap) {
        this.target = target;
        this.interceptor = interceptor;
        this.signatureMap = signatureMap;
    }

    // 如果自定义的拦截器上没有添加注解，没有要拦截的目标，则直接返回target，反之则使用Proxy动态代理
    public static Object wrap(Object target, Interceptor interceptor) {
        Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
        Class<?> type = target.getClass();
        // 获取需要被代理类的所有待拦截的接口
        Class<?>[] interfaces = getAllInterfaces(type, signatureMap);
        if (interfaces.length > 0) {
            // 创建代理类
            return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                    interfaces, new Plugin(target, interceptor, signatureMap));
        }
        // 没有需要拦截的方法，直接返回原实例
        return target;
    }

    // 获取需要被拦截的方法摘要
    // 拿到实现Interceptor接口的类型所添加的所有注解
    // @Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
    private static Map<Class<?>, Set<Method>> getSignatureMap(Interceptor interceptor) {
        // 先获取拦截器实现类上的注解，提取需要被拦截的方法
        // Intercepts interceptsAnnotation = interceptor.getClass().getAnnotation(Intercepts.class);
        // if (interceptsAnnotation == null) {
        //     throw new PluginException("No @Intercepts annotation was found in interceptor "
        //     + interceptor.getClass().getName());
        // }
        // Signature[] sigs = interceptsAnnotation.value();
        Map<Class<?>, Set<Method>> signatureMap = new HashMap<>();
        // for (Signature sig : sigs) {
        //     Set<Method> methods = MapUtil.computeIfAbsent(signatureMap, sig.type(), k -> new HashSet<>());
        //     try {
        //         根据方法名以及参数获取待拦截方法
        //         Method method = sig.type().getMethod(sig.method(), sig.args());
        //         methods.add(method);
        //     } catch (NoSuchMethodException e) {
        //         throw new PluginException("Could not find method on "
        //         + sig.type() + " named " + sig.method() + ". Cause: " + e, e);
        //     }
        // }
        return signatureMap;
    }

    // 拿到type类型所实现的所有接口
    private static Class<?>[] getAllInterfaces(Class<?> type, Map<Class<?>, Set<Method>> signatureMap) {
        Set<Class<?>> interfaces = new HashSet<>();
        while (type != null) {
            for (Class<?> c : type.getInterfaces()) {
                if (signatureMap.containsKey(c)) {
                    interfaces.add(c);
                }
            }
            type = type.getSuperclass();
        }
        return interfaces.toArray(new Class<?>[0]);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            Set<Method> methods = signatureMap.get(method.getDeclaringClass());
            // 判断是否为待拦截方法，判断所要执行的method是否在自动的Interceptor中注解中声明
            // 动态判断，所有在拦截器多的时候，会影响性能
            if (methods != null && methods.contains(method)) {
                // 通过代理，触发invoke()方法，调用到自定义拦截器的.intercept()方法
                return interceptor.intercept(new Invocation(target, method, args));
            }
            // 反射调用指定对象target上的方法，必跟提供参数args
            return method.invoke(target, args);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error");
        }
    }
}
