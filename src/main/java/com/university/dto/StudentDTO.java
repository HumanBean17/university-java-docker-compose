package com.university.dto;

import com.university.entity.Group;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    String id;
    String name;
    Group group;
    Integer visitPercentage;
}
