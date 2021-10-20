package com.university.repository;

import com.university.entity.Student;

import java.util.Map;
import java.util.UUID;

public interface RedisRepository {
    Map<String, Object> findAllStudents();
    void save(Student student);
    void delete(String id);
    Student findStudentById(UUID id);
}
