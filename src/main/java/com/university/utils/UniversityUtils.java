package com.university.utils;

import com.university.entity.Group;
import com.university.entity.Student;

import java.util.HashSet;
import java.util.UUID;

public class UniversityUtils {

    public static Group getRandomGroup(HashSet<Student> students) {
        Group group = new Group();
        group.setId(UUID.randomUUID());
        group.setGroupCode(UUID.randomUUID());
        group.setStudents(students);
        students.forEach(student -> student.setGroupEntity(group));
        return group;
    }

    public static Student getRandomStudent(Group group) {
        Student student = new Student();
        student.setId(UUID.randomUUID().toString());
        student.setGroupEntity(group);
        return student;
    }
}
