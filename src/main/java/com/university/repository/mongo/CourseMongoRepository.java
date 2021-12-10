package com.university.repository.mongo;

import com.university.entity.mongo.CourseMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface CourseMongoRepository extends MongoRepository<CourseMongo, UUID> {
}
