package com.university.rest;

import com.university.dto.FindStudentsDTO;
import com.university.dto.LabOneDTO;
import com.university.dto.LectureDTO;
import com.university.entity.Group;
import com.university.entity.LectureText;
import com.university.entity.Student;
import com.university.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.university.utils.UniversityUtils.getRandomGroup;
import static com.university.utils.UniversityUtils.getRandomStudent;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityService universityService;

    @PostMapping(value = "/lab_1", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LabOneDTO> lab_1() {
        return new LinkedList<>();
    }

    @PostMapping(value = "/findLectureByTextEntry", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LectureText> findLectureByTextEntry(@RequestBody FindStudentsDTO dto) {
        return universityService.findByTextEntry(dto.getLecturePhrase());
    }

    @PostMapping(value = "/addLecture", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addLecture(@RequestBody LectureDTO dto) {
        universityService.saveLecture(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/getLectures", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LectureText> getLectures() {
        return universityService.getAllLectures();
    }

    @PostMapping(value = "/addStudent", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
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
