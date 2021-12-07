package com.university.repository.redis;

import com.university.entity.redis.StudentRedis;

import java.util.Map;

public interface StudentRedisRepository {
    Map<String, StudentRedis> findAllStudents();
    void save(StudentRedis student);
    void delete(String id);
    StudentRedis findStudentById(String id);
}
