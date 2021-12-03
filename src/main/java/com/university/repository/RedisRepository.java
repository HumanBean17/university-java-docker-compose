package com.university.repository;

import com.university.entity.Student;
import com.university.entity.StudentHash;

import java.util.Map;
import java.util.UUID;

public interface RedisRepository {
    Map<String, StudentHash> findAllStudents();
    void save(StudentHash student);
    void delete(String id);
    StudentHash findStudentById(String id);
}
