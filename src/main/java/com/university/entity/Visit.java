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
@Table(name = "visit")
public class Visit implements Serializable {

    @Id
    @Column(name = "id")
    UUID id;
    @Column(name = "visited")
    boolean visited;

    @OneToOne
    @JoinColumn(name = "schedule_id")
    Schedule schedule;
    @OneToOne
    @JoinColumn(name = "student_id")
    Student student;
}
