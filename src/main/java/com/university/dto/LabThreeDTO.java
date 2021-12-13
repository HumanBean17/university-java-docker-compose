package com.university.dto;

import com.university.entity.Student;
import lombok.*;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LabThreeDTO implements Serializable {

    List<StudentDTO> students = new LinkedList<>();
}
