package com.university.repository;

import com.university.entity.Student;
import com.university.entity.StudentHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.UUID;

@Repository
public class RedisRepositoryImpl implements RedisRepository {

    private static final String STUDENT_KEY = "student";
    private final RedisTemplate<String, StudentHash> redisTemplate;
    private HashOperations<String, String, StudentHash> hashOperations;

    @Autowired
    public RedisRepositoryImpl(RedisTemplate<String, StudentHash> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(final StudentHash student) {
        hashOperations.put(STUDENT_KEY, student.getId(), student);
    }

    @Override
    public void delete(final String id) {
        hashOperations.delete(STUDENT_KEY, id);
    }

    @Override
    public StudentHash findStudentById(final String id){
        return hashOperations.get(STUDENT_KEY, id);
    }

    @Override
    public Map<String, StudentHash> findAllStudents(){
        return hashOperations.entries(STUDENT_KEY);
    }
}