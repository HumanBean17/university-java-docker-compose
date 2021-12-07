package com.university.repository.redis;

import com.university.entity.redis.StudentRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
public class StudentRedisRepositoryImpl implements StudentRedisRepository {

    private static final String STUDENT_KEY = "student";
    private final RedisTemplate<String, StudentRedis> redisTemplate;
    private HashOperations<String, String, StudentRedis> hashOperations;

    @Autowired
    public StudentRedisRepositoryImpl(RedisTemplate<String, StudentRedis> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(final StudentRedis student) {
        hashOperations.put(STUDENT_KEY, student.getId(), student);
    }

    @Override
    public void delete(final String id) {
        hashOperations.delete(STUDENT_KEY, id);
    }

    @Override
    public StudentRedis findStudentById(final String id){
        return hashOperations.get(STUDENT_KEY, id);
    }

    @Override
    public Map<String, StudentRedis> findAllStudents(){
        return hashOperations.entries(STUDENT_KEY);
    }
}