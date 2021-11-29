package com.university.service;

import com.university.dto.LectureDTO;
import com.university.entity.*;
import com.university.mapper.LectureMapper;
import com.university.repository.*;
import com.university.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    // Elastic
    private final LectureTextRepository lectureTextRepository;
    // Redis
    private final RedisRepository redisRepository;

    // Postgres
    private final LectureRepository lectureRepository;
    private final GroupRepository groupRepository;
    private final VisitRepository visitRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional(readOnly = true)
    public List<Student> labOneQuery(String lecturePhrase) {
        List<LectureText> lectures = findByTextEntry(lecturePhrase);

        List<Schedule> schedules = new LinkedList<>();
        List<Visit> visits = new LinkedList<>();
        List<Student> students = new LinkedList<>();

        for (LectureText lectureText : lectures) {
            schedules.addAll(scheduleRepository.findByLectureId(UUID.fromString(lectureText.getId())));
        }

        for (Schedule schedule : schedules) {
            visits.addAll(visitRepository.findAllByScheduleId(schedule.getId()));
        }

        for (Visit visit : visits) {
            students.add(visit.getStudent());
        }

        return students;
    }

    @Transactional
    public String labOneData() {
        // Saving Group with Students
        Set<Student> students = new HashSet<>();
        for (int i = 0; i < 30; i++) {
            students.add(Utils.getRandomStudent(null));
        }
        Group group = Utils.getRandomGroup(students);
        HashSet<Group> groups = new HashSet<>();
        groups.add(group);
        saveGroup(group);

        // Saving Lecture
        LectureDTO lecture = Utils.getRandomLecture(null);
        HashSet<LectureDTO> lectures = new HashSet<>();
        lectures.add(lecture);
        saveLecture(lecture);

        // Saving Schedule
        Schedule schedule = Utils.getRandomSchedule(lecture, groups);
        saveSchedule(schedule);

        // Saving visit
        for (Student student : students) {
            Visit visit = Utils.getRandomVisit(schedule, student);
            saveVisit(visit);
        }
        return lecture.getText();
    }

    /***
     * VISIT
     */
    @Transactional
    public Visit saveVisit(Visit visit) {
        return visitRepository.save(visit);
    }

    /***
     * SCHEDULE
     */
    @Transactional
    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    /***
     *
     * LECTURE
     */
    @Transactional(readOnly = true)
    public List<LectureText> findByTextEntry(String textEntry) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("text", textEntry))
                .build();
        return elasticsearchRestTemplate.search(searchQuery, LectureText.class)
                .getSearchHits()
                .stream()
                .map(SearchHit::getContent).collect(Collectors.toList());
    }

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

    /***
     *
     * STUDENT
     */
    @Transactional
    public void saveStudent(Student student) {
        redisRepository.save(student);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getAllStudents() {
        return redisRepository.findAllStudents();
    }

    /***
     *
     * GROUP
     */
    @Transactional
    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    @Transactional(readOnly = true)
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
}
