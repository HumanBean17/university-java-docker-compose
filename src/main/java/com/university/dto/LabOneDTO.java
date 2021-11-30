package com.university.dto;

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

    List<StudentDTO> students = new LinkedList<>();
    LocalDateTime from;
    LocalDateTime to;
    String phrase;
}
