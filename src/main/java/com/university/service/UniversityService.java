package com.university.service;

import com.university.dto.LectureDTO;
import com.university.entity.Group;
import com.university.entity.Lecture;
import com.university.entity.LectureText;
import com.university.entity.Student;
import com.university.repository.GroupRepository;
import com.university.repository.LectureRepository;
import com.university.repository.LectureTextRepository;
import com.university.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final LectureTextRepository lectureTextRepository;
    private final LectureRepository lectureRepository;

    private final GroupRepository groupRepository;
    private final RedisRepository redisRepository;

    @Transactional(readOnly = true)
    public List<LectureText> findByTextEntry(String textEntry) {
        return lectureTextRepository.findLectureTextByTextContains(textEntry);
    }

//    @Transactional
//    public void lab_1() {
//        List<LectureText> lst = lectureTextRepository.findLectureTextByTextContains(textEntry);
//
//    }

    @Transactional
    public void saveLecture(LectureDTO dto) {
        Lecture lecture = new Lecture(dto.getId(), dto.getName(), dto.getSubject());
        LectureText lectureText = new LectureText(dto.getId().toString(), dto.getText());
        lectureRepository.save(lecture);
        lectureTextRepository.save(lectureText);
    }

    @Transactional(readOnly = true)
    public List<LectureText> getAllLectures() {
        List<LectureText> lst = new ArrayList<>();
        lectureTextRepository.findAll().forEach(lst::add);
        return lst;
    }

//    @Transactional(readOnly = true)
//    public List<Lecture> getLectureByText(String containedText) {
//        return lectureRepository.findAll();
//    }

    @Transactional
    public void saveStudent(Student student) {
        redisRepository.save(student);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getAllStudents() {
        return redisRepository.findAllStudents();
    }

    @Transactional
    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    @Transactional(readOnly = true)
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
}
