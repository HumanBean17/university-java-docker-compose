package com.university.dto;

import com.university.entity.Student;
import lombok.*;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LabThreeDTO implements Serializable {

    Set<CourseDTO> courses = new HashSet<>();
    Set<StudentDTO> students = new HashSet<>();
}
