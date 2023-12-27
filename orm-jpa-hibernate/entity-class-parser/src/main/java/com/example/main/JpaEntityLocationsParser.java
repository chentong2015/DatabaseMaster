package com.example.main;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.reflections.Reflections;

import javax.persistence.Entity;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class JpaEntityLocationsParser {

    // Unmarshaller: 将xml文件中配置的信息映射到@XmlRootElement对象中
    public static void main(String[] args) throws JAXBException {
        URL jpaLocationsFile = JpaEntityLocationsParser.class.getResource("/jpa-entity-locations.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(JpaEntityLocations.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        JpaEntityLocations jpaEntityLocations = (JpaEntityLocations) unmarshaller.unmarshal(jpaLocationsFile);
        parseJpaEntities(jpaEntityLocations);
    }

    // 根据Packages包路径解析所有的JpaEntities => 通过反射操作Class<?>
    public static void parseJpaEntities(JpaEntityLocations jpaEntityLocations) {
        Set<Class<?>> jpaEntities = new HashSet<>();
        for (String packageName : jpaEntityLocations.getPackages()) {
            Reflections reflections = new Reflections(packageName);
            jpaEntities.addAll(reflections.getTypesAnnotatedWith(Entity.class));
        }
        for (Class<?> entity : jpaEntities) {
            System.out.println(entity.getName());
        }
    }
}
