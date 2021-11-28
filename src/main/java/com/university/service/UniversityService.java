package com.university.service;

import com.university.entity.Group;
import com.university.entity.Lecture;
import com.university.entity.Student;
import com.university.repository.GroupRepository;
import com.university.repository.LectureRepository;
import com.university.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final LectureRepository lectureRepository;
    private final GroupRepository groupRepository;
    private final RedisRepository redisRepository;

    @Transactional
    public void saveLecture(Lecture lecture) {
        lectureRepository.save(lecture);
    }

//    @Transactional(readOnly = true)
//    public Lecture getLectureByText(String containedText) {
//        lectureRepository
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
