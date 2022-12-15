package com.mongodb.main.repository;

import com.mongodb.main.model.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

// 配置document和对应的key主键
public interface PersonRepository extends ReactiveMongoRepository<Person, String> {
    
}
