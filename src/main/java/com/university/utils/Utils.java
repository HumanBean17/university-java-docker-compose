package com.university.utils;

import com.university.dto.LectureDTO;
import com.university.entity.*;
import com.university.mapper.LectureMapper;

import java.time.LocalDateTime;
import java.util.*;

public class Utils {

    public static final Random rand = new Random();

    public static Visit getRandomVisit(Schedule schedule, Student student) {
        Visit visit = new Visit();
        visit.setId(UUID.randomUUID());
        visit.setVisited(rand.nextBoolean());
        visit.setSchedule(schedule);
        visit.setStudent(student);
        return visit;
    }

    public static LectureDTO getRandomLecture(Subject subject) {
        LectureDTO lecture = new LectureDTO();
        lecture.setId(UUID.randomUUID());
        lecture.setName(UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT));
        lecture.setText(Data.BUILD_PROECT_USE_INF_SYS_LECTURES.get(0)); //TODO
        lecture.setSubject(subject);
        return lecture;
    }

    public static Schedule getRandomSchedule(LectureDTO lectureDTO, Set<Group> groups) {
        Schedule schedule = new Schedule();
        schedule.setId(UUID.randomUUID());
        schedule.setDate(LocalDateTime.now());
        schedule.setGroups(groups);
        schedule.setLecture(LectureMapper.dtoToPostgreEntity(lectureDTO));
        return schedule;
    }

    public static Group getRandomGroup(Set<Student> students) {
        Group group = new Group();
        group.setId(UUID.randomUUID());
        group.setGroupCode(UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT));
        group.setStudents(students);
        students.forEach(student -> student.setGroupEntity(group));
        return group;
    }

    public static Student getRandomStudent(Group group) {
        Student student = new Student();
        student.setId(UUID.randomUUID().toString());
        student.setName(Data.NAMES.get(rand.nextInt(Data.NAMES.size())));
        student.setGroupEntity(group);
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
