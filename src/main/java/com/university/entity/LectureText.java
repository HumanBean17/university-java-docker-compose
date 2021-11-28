package com.university.entity;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName = "lectureindex")
public class LectureText implements Serializable {

    @Id
    @Column(name = "id")
    String id;

    @Column(name = "text")
    @Field(type = FieldType.Text, name = "text")
    String text;

}
