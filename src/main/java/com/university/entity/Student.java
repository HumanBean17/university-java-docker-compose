package com.university.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@RedisHash(value = "student")
@Table(name = "student")
public class Student implements Serializable {

    @Id
    @Column(name = "id")
    String id;
    @Column(name = "name")
    String name;

    @ToString.Exclude
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "group_id")
    Group groupEntity;
}
