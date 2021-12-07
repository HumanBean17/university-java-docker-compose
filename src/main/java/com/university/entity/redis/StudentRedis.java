package com.university.entity.redis;

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
    String name;
}
