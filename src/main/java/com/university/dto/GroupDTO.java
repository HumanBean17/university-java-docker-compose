package com.university.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    Set<StudentDTO> students = new HashSet<>();
    @JsonIgnore
    SpecialityDTO speciality;
}
