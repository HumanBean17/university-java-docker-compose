package com.university.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document("student")
public class StudentMongo implements Serializable {

    @Id
    String id;
    @Field(name = "name")
    String name;
}
