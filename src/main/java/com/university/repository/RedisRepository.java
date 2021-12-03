package com.university.repository;

import com.university.entity.StudentRedis;

import java.util.Map;

public interface RedisRepository {
    Map<String, StudentRedis> findAllStudents();
    void save(StudentRedis student);
    void delete(String id);
    StudentRedis findStudentById(String id);
}
