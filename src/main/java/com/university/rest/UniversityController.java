package com.university.rest;

import com.university.dto.FindStudentsDTO;
import com.university.dto.LabOneDTO;
import com.university.dto.LectureDTO;
import com.university.dto.StudentDTO;
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
    public List<LectureElastic> findLectureByTextEntry(@RequestBody FindStudentsDTO dto) {
        return universityService.findByTextEntry(dto.getLecturePhrase());
    }
    @PostMapping(value = "/addLecture", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addLecture(@RequestBody LectureDTO dto) {
        universityService.saveLecture(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping(value = "/getLectures", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LectureElastic> getLectures() {
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
    public ResponseEntity<String> addStudent(@RequestBody StudentDTO student) {
        universityService.saveStudent(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping(value = "/getAllStudentsRedis", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, StudentRedis> getAllStudents() {
        return universityService.getAllStudents();
    }

    /***
     *
     * GROUP API
     */
    @GetMapping(value = "/saveRandomGroup", produces = MediaType.APPLICATION_JSON_VALUE)
    public void saveRandomGroup() {
        Group group = getRandomGroup();
        Set<Student> students = new HashSet<>();
        Set<StudentDTO> studentDTOS = new HashSet<>();
        for (int i = 0; i < 30; i++) {
            StudentDTO studentDTO = getRandomStudent(group);
            students.add(new Student(studentDTO.getId(), studentDTO.getGroup()));
            studentDTOS.add(studentDTO);
        }
        group.getStudents().addAll(students);
        universityService.saveGroup(group);
        for (StudentDTO student : studentDTOS) {
            universityService.saveStudent(student);
        }
    }

    @GetMapping(value = "/getAllGroupsMongo", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GroupMongo> getAllGroupsMongo() {
        return universityService.getAllGroupsMongo();
    }

    @GetMapping(value = "/getAllGroups", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GroupMongo> getAllGroups() {
        return universityService.getAllGroupsMongo();
//        return universityService.getAllGroups();
    }

}
