package com.university.mapper;

import com.university.dto.StudentDTO;
import com.university.entity.Student;

public class StudentMapper {

    public static Student dtoToPostgres(StudentDTO studentDTO) {
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setName(student.getName());
//        student.setGroupEntity(student.getGroupEntity());
        return student;
    }
}
