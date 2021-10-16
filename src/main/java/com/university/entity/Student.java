package com.university.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "student")
public class Student implements Serializable {

    @Id
    @Column(name = "id")
    UUID id;

    @JoinColumn(name = "group_id")
    @OneToOne(targetEntity = Group.class)
    @JsonBackReference
    Group groupEntity;
}
