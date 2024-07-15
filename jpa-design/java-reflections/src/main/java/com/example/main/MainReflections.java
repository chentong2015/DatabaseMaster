package com.example.main;

import com.example.main.data.MyEntityData;
import com.example.main.model.Project;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.beans.JavaBean;
import java.util.Set;

import static org.reflections.scanners.Scanners.SubTypes;
import static org.reflections.scanners.Scanners.TypesAnnotated;

// 0.9.8 版本不支持以下API
// org.reflections.scanners.Scanners;
public class MainReflections {

    public static void main(String[] args) {
        String dtoPackageName = MyEntityData.class.getPackageName();

        // 在扫描具有特殊注解的的类型时，需要添加package路径的过滤器，否则会全模块扫描
        // 在某种Spring的特殊环境下，Filter过滤器将会失效，需要自定义提供过滤条件 !!
        new FilterBuilder().add(path -> path.contains("com.example.main"));
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder()
                .forPackage(dtoPackageName)
                .setScanners(Scanners.TypesAnnotated, Scanners.FieldsAnnotated, Scanners.MethodsAnnotated)
                .setInputsFilter(path -> path.contains("com.package.path"))
                .filterInputsBy(path -> path.contains(dtoPackageName));
        Reflections reflections = new Reflections(configurationBuilder);
        System.out.println("done");
    }

    // A filter tells the scanners what to include and what to exclude when scanning the classpath.
    // ConfigurationBuilder configurationBuilder = new ConfigurationBuilder()
    //         .forPackage(dtoPackageName)
    //         .addScanners(Scanners.TypesAnnotated)
    //         .addScanners(Scanners.FieldsAnnotated)
    //         .addScanners(Scanners.MethodsAnnotated)
    //         .filterInputsBy(new FilterBuilder().includePackage(dtoPackageName));
    // Reflections reflections = new Reflections(configurationBuilder);
    private static void testReflectionFilter() {
        // TypesAnnotated扫描的是类上的注解
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("com.example.reflections"))
                .setScanners(SubTypes, TypesAnnotated)
                .filterInputsBy(new FilterBuilder()
                        .includePackage("com.reflections")
                        .excludePackage("com.reflections.test")));
        System.out.println(reflections);
    }

    private static void testBaseReflections() {
        Reflections reflections = new Reflections("com.example.main.model");

        // 反射：获取指定类型的继承类型
        Set<Class<?>> subTypes = reflections.get(SubTypes.of(Project.class).asClass());
        System.out.println(subTypes.stream().findFirst().get().getName());

        // 反射：获取添加了指定注解的java class
        Set<Class<?>> annotated = reflections.get(SubTypes.of(TypesAnnotated.with(JavaBean.class)).asClass());
        System.out.println(annotated.stream().findFirst().get().getName());
    }
}
