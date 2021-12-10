package com.university.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "subject")
public class Subject implements Serializable {

    @Id
    @Column(name = "id")
    UUID id;
//    @Column(name = "name")
//    String name;

    @ManyToOne
    @JoinColumn(name = "course_id")
    Course course;
}
