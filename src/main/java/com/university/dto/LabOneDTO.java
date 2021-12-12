package com.university.dto;

import com.university.entity.StudentRedis;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LabOneDTO {

//    List<StudentRedis> students = new LinkedList<>();
    List<StudentDTO> students = new LinkedList<>();
    LocalDateTime from;
    LocalDateTime to;
    String phrase;
}
