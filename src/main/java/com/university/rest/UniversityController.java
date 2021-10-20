package com.university.rest;

import com.university.entity.Group;
import com.university.entity.Student;
import com.university.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.university.utils.UniversityUtils.getRandomGroup;
import static com.university.utils.UniversityUtils.getRandomStudent;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityService universityService;

    @PostMapping(value = "/addStudent", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody Student student) {
        universityService.saveStudent(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/getAllStudents", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getAllStudents() {
        return universityService.getAllStudents();
    }

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

}
