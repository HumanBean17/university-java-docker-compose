package com.university.repository.mongo;

import com.university.entity.mongo.StudentMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface StudentMongoRepository extends MongoRepository<StudentMongo, UUID> {
}
