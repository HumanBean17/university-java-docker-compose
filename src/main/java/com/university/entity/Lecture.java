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
//@Entity
//@Table(name = "lecture")
@Document(indexName = "lectureindex")
public class Lecture implements Serializable {

    @Id
//    @Column(name = "id")
    String id;
//    @Column(name = "name")
//    @Field(type = FieldType.Text, name = "name")
//    String name;
////    @Column(name = "text")
//    @Field(type = FieldType.Text, name = "text")
//    String text;

//    @ManyToOne
//    @JoinColumn(name = "subject_id")
//    @Field(type = FieldType.Nested, includeInParent = true)
//    Subject subject;
}
