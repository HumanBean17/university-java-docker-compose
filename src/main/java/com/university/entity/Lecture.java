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
@Entity
@Table(name = "lecture")
public class Lecture implements Serializable {

    @Id
    @Column(name = "id")
    UUID id;

    @Column(name = "name")
    String name;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    Subject subject;
}
