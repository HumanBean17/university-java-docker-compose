package com.university.dto;

import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO implements Serializable {

    UUID id;
    String groupCode;
    Set<StudentDTO> students = new HashSet<>();
    SpecialityDTO speciality;
}
