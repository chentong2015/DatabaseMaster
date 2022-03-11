package com.mongodb.main;

import com.mongodb.main.model.Person;
import com.mongodb.main.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

// MongoDB document-oriented database
// 1. 面向文档的数据库
// 2. 以collection(包含json)的形式来存储
// https://docs.mongodb.com/manual/
// https://docs.mongodb.com/manual/reference/connection-string/
// https://www.digitalocean.com/community/tutorials/how-to-use-mongodb-compass GUI的使用
@SpringBootApplication
public class BaseMongoDB {

    private final Logger logger = LogManager.getLogger(BaseMongoDB.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(BaseMongoDB.class, args);
    }

    // TODO: 通过回调接口，在Spring boot启动后执行一定的逻辑
    // The CommandLineRunner is a callback interface in Spring Boot
    // When Spring Boot starts will call it and pass in args through a run() internal method.
    @Bean
    CommandLineRunner start(PersonRepository personRepository) {
        return args -> {
            // Create a list of persons and store them in our MongoDB
            Flux.just(new Person("josdem", "joseluis.delacruz@gmail.com"),
                            new Person("tgrip", "tgrip@email.com"),
                            new Person("edzero", "edzero@email.com"),
                            new Person("siedrix", "siedrix@email.com"),
                            new Person("mkheck", "mkheck@email.com"))
                    .flatMap(personRepository::save)  // 配置映射关联
                    .subscribe(person -> logger.info("person: {}", person)); // 在这一步时，数据才开始流动
        };
    }
}
