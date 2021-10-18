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
@Table(name = "class")
public class Clazz implements Serializable {

    @Id
    @Column(name = "id")
    UUID id;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    Subject subject;
}
