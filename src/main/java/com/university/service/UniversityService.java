package com.university.service;

import com.university.dto.FindStudentsDTO;
import com.university.dto.LabOneDTO;
import com.university.dto.LectureDTO;
import com.university.dto.StudentDTO;
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
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
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
    private final StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public LabOneDTO labOneQuery(FindStudentsDTO findStudentsDTO) {
        List<LectureText> lectures = findByTextEntry(findStudentsDTO.getLecturePhrase());

        List<Schedule> schedules = new LinkedList<>();
        List<Visit> visits = new LinkedList<>();
        List<Student> students = new LinkedList<>();

        for (LectureText lectureText : lectures) {
            schedules.addAll(scheduleRepository.findByLectureIdAndDateBetween(
                    UUID.fromString(lectureText.getId()),
                    findStudentsDTO.getFrom(),
                    findStudentsDTO.getTo()));
        }

        for (Schedule schedule : schedules) {
            visits.addAll(visitRepository.findAllByScheduleId(schedule.getId()));
        }

        Map<String, Integer> visited = new HashMap<>();
        Map<String, Integer> unvisited = new HashMap<>();
        SortedMap<String, Integer> percentVisit = new TreeMap<>();
        for (Visit visit : visits) {
            String id = visit.getStudent().getId();
            if (visit.isVisited()) {
                visited.put(id, visited.get(id) == null ? 1 : visited.get(id) + 1);
            } else {
                unvisited.put(id, unvisited.get(id) == null ? 1 : unvisited.get(id) + 1);
            }
        }
        for (Map.Entry<String, Integer> elem : unvisited.entrySet()) {
            if (visited.get(elem.getKey()) != null) {
                percentVisit.put(elem.getKey(), elem.getValue() * 100 / (elem.getValue() + visited.get(elem.getKey())));
            } else {
                percentVisit.put(elem.getKey(), 0);
            }
        }
        for (Map.Entry<String, Integer> elem : visited.entrySet()) {
            if (unvisited.get(elem.getKey()) != null) {
                percentVisit.put(elem.getKey(), elem.getValue() * 100 / (elem.getValue() + unvisited.get(elem.getKey())));
            } else {
                percentVisit.put(elem.getKey(), 100);
            }
        }

        LabOneDTO result = new LabOneDTO();
        Set<Map.Entry<String, Integer>> set = Utils.entriesSortedByValues(percentVisit);
        int i = 0;
        for (Map.Entry<String, Integer> elem : set) {
            result.getStudents().add(
                    new StudentDTO(elem.getKey(), studentRepository.findById(elem.getKey()).get().getName(), elem.getValue()));
            if (i >= findStudentsDTO.getNumber()) {
                break;
            }
            i++;
        }
        result.setFrom(findStudentsDTO.getFrom());
        result.setTo(findStudentsDTO.getTo());
        result.setPhrase(findStudentsDTO.getLecturePhrase());
        return result;
    }

    @Transactional
    public String labOneData() {
        // Saving Group with Students
        Set<Student> students = new HashSet<>();
        Set<Group> groups = new HashSet<>();

        for (int i = 0; i < 3; i++) {
            Set<Student> tmpStudents = new HashSet<>();
            for (int j = 0; j < 30; j++) {
                tmpStudents.add(Utils.getRandomStudent(null));
            }
            students.addAll(tmpStudents);
            Group group = Utils.getRandomGroup(tmpStudents);
            groups.add(group);
            saveGroup(group);
        }

        // Saving Lecture
        HashSet<LectureDTO> lectures = new HashSet<>();
        LectureDTO lecture = Utils.getRandomLecture(null);
        lectures.add(lecture);
        saveLecture(lecture);

        Schedule schedule = Utils.getRandomSchedule(lecture, groups);
        saveSchedule(schedule);

        for (Student student : students) {
            Visit visit = Utils.getRandomVisit(schedule, student);
            saveVisit(visit);
        }

        lectures = new HashSet<>();
        lecture = Utils.getRandomLecture(null);
        lectures.add(lecture);
        saveLecture(lecture);

        schedule = Utils.getRandomSchedule(lecture, groups);
        saveSchedule(schedule);

        for (Student student : students) {
            Visit visit = Utils.getRandomVisit(schedule, student);
            saveVisit(visit);
        }
        return "ok";
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
                .withQuery(matchPhraseQuery("text", textEntry))
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
