package com.university.rest;

import com.university.entity.Group;
import com.university.entity.Student;
import com.university.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class UniversityController {

    @Autowired
    private UniversityService universityService;

    @GetMapping(value = "/saveRandomGroup", produces = MediaType.APPLICATION_JSON_VALUE)
    public Group saveRandomGroup() {
        return universityService.saveGroup(getRandomGroup(new HashSet<Student>() {{
            add(getRandomStudent(null));
        }}));
    }

    @GetMapping(value = "/getAllGroups", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Group> getAllGroups() {
        return universityService.getAllGroups();
    }

    private Group getRandomGroup(HashSet<Student> students) {
        Group group = new Group();
        group.setId(UUID.randomUUID());
        group.setGroupCode(UUID.randomUUID());
        group.setStudents(students);
        students.forEach(student -> student.setGroupEntity(group));
        return group;
    }

    private Student getRandomStudent(Group group) {
        return new Student(UUID.randomUUID(), group);
    }
}
