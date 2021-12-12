package com.university.entity.mongo;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document
public class CourseMongo implements Serializable {

    @Id
    UUID id;

    @Field(name = "name")
    String name;
    @Field(name = "hour")
    Integer hour;

    Set<SubjectMongo> subjects = new HashSet<>();
}
