package com.university.dto;

import com.university.entity.Subject;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LectureDTO implements Serializable {

    UUID id;
    String name;
    String text;
    Subject subject;
}
