package com.university.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document//("group")
public class GroupMongo implements Serializable {

    @Id
    UUID id;

    @Field("group_code")
    String groupCode;

    @DBRef
    Set<StudentMongo> students = new HashSet<>();

}
