package com.university.entity;

import com.university.dto.StudentDTO;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@RedisHash(value = "student")
public class StudentRedis implements Serializable {

    String id;
    StudentDTO studentDTO;

}
