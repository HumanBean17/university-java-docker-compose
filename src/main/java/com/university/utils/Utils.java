package com.university.utils;

import com.university.dto.LectureDTO;
import com.university.dto.StudentDTO;
import com.university.entity.*;
import com.university.mapper.LectureMapper;

import java.time.LocalDateTime;
import java.util.*;

public class Utils {

    public static final Random rand = new Random();

    public static Visit getRandomVisit(Schedule schedule, StudentDTO studentDTO) {
        Visit visit = new Visit();
        visit.setId(UUID.randomUUID());
        visit.setVisited(rand.nextBoolean());
        visit.setSchedule(schedule);
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setGroupEntity(studentDTO.getGroup());
        visit.setStudent(student);
        return visit;
    }

    public static HashSet<LectureDTO> getLectures(Subject bigDockerSubj, Subject linuxSubj) {
        HashSet<LectureDTO> lectures = new HashSet<>();
        LectureDTO lecture;
        for (String elem : Data.BIG_DOCKER_LECTURES) {
            lecture = new LectureDTO();
            lecture.setId(UUID.randomUUID());
            lecture.setName(UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT));
            lecture.setText(elem);
            lecture.setSubject(bigDockerSubj);
            lectures.add(lecture);
        }
        for (String elem : Data.LINUX_LECTURES) {
            lecture = new LectureDTO();
            lecture.setId(UUID.randomUUID());
            lecture.setName(UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT));
            lecture.setText(elem);
            lecture.setSubject(linuxSubj);
            lectures.add(lecture);
        }
        return lectures;
    }

    public static Schedule getRandomSchedule(LectureDTO lectureDTO, Set<Group> groups) {
        Schedule schedule = new Schedule();
        schedule.setId(UUID.randomUUID());
        schedule.setDate(LocalDateTime.now());
        schedule.setGroups(groups);
        schedule.setLecture(LectureMapper.dtoToPostgreEntity(lectureDTO));
        return schedule;
    }

    public static Group getRandomGroup(Set<StudentDTO> students) {
        Group group = new Group();
        group.setId(UUID.randomUUID());
        group.setGroupCode(UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT));
        for (StudentDTO studentDTO : students) {
            Student student = new Student(studentDTO.getId(), studentDTO.getGroup());
            group.getStudents().add(student);
        }
        students.forEach(student -> student.setGroup(group));
        return group;
    }

    public static StudentDTO getRandomStudent(Group group) {
        StudentDTO student = new StudentDTO();
        student.setId(UUID.randomUUID().toString());
        student.setName(Data.NAMES.get(rand.nextInt(Data.NAMES.size())));
        student.setGroup(group);
        return student;
    }

    public static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
                new Comparator<Map.Entry<K,V>>() {
                    @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                        int res = e1.getValue().compareTo(e2.getValue());
                        return res != 0 ? res : 1;
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
}
