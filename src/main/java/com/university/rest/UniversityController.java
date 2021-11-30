package com.university.rest;

import com.university.dto.FindStudentsDTO;
import com.university.dto.LabOneDTO;
import com.university.dto.LectureDTO;
import com.university.entity.*;
import com.university.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.university.utils.Utils.*;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityService universityService;

    @PostMapping(value = "/labOneQuery", consumes = MediaType.APPLICATION_JSON_VALUE)
    public LabOneDTO labOneQuery(@RequestBody FindStudentsDTO findStudentsDTO) {
        return universityService.labOneQuery(findStudentsDTO);
    }

    @GetMapping(value = "/labOneData")
    public String labOneData() {
        return universityService.labOneData();
    }

    /***
     *
     * LECTURE API
     */
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

    /***
     * SCHEDULE API
     */
    @GetMapping(value = "/saveRandomSchedule", produces = MediaType.APPLICATION_JSON_VALUE)
    public Schedule saveRandomSchedule() {
        return universityService.saveSchedule(getRandomSchedule(null, null));
    }

    /***
     *
     * STUDENT API
     */
    @PostMapping(value = "/addStudent", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        universityService.saveStudent(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping(value = "/getAllStudents", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getAllStudents() {
        return universityService.getAllStudents();
    }

    /***
     *
     * GROUP API
     */
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
