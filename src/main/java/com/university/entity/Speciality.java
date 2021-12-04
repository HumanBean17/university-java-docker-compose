package com.university.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "speciality")
public class Speciality implements Serializable {

    @Id
    @Column(name = "id")
    UUID id;

    @Column(name = "name")
    String name;
}
