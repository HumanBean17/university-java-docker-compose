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
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    // Elastic
    private final LectureElasticRepository lectureElasticRepository;
    // Redis
    private final RedisRepository redisRepository;
    // Mongo
    private final GroupMongoRepository groupMongoRepository;
    private final StudentMongoRepository studentMongoRepository;

    // Postgres
    private final LectureRepository lectureRepository;
    private final GroupRepository groupRepository;
    private final VisitRepository visitRepository;
    private final ScheduleRepository scheduleRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Transactional(readOnly = true)
    public LabOneDTO labOneQuery(FindStudentsDTO findStudentsDTO) {
        List<LectureElastic> lectures = findByTextEntry(findStudentsDTO.getLecturePhrase());

        List<Schedule> schedules = new LinkedList<>();
        List<Visit> visits = new LinkedList<>();
        List<Student> students = new LinkedList<>();

        for (LectureElastic lectureElastic : lectures) {
            schedules.addAll(scheduleRepository.findByLectureIdAndDateBetween(
                    UUID.fromString(lectureElastic.getId()),
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
            Student student = studentRepository.findById(elem.getKey()).get();
            StudentRedis studentRedis = redisRepository.findStudentById(elem.getKey());
            result.getStudents().add(
                    new StudentDTO(student.getId(), studentRedis.getName(), student.getGroupEntity(), elem.getValue()));
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
        Set<StudentDTO> students = new HashSet<>();
        Set<Group> groups = new HashSet<>();

        Group group = Utils.getRandomGroup();
        saveGroup(group);

        for (int i = 0; i < 3; i++) {
            Set<StudentDTO> tmpStudents = new HashSet<>();

            for (int j = 0; j < 30; j++) {
                StudentDTO studentDTO = Utils.getRandomStudent(group);
                Student student = new Student(studentDTO.getId(), studentDTO.getGroup());
                tmpStudents.add(studentDTO);
                group.getStudents().add(student);

                saveStudent(studentDTO);
            }
            students.addAll(tmpStudents);
            groups.add(group);

        }

        // Saving Subjects
        Subject bigDockerSubj = new Subject();
        bigDockerSubj.setId(UUID.randomUUID());
        bigDockerSubj.setName("Принципы построения, проектирования и эксплуатации информационных систем");
        subjectRepository.save(bigDockerSubj);
        Subject linuxSubj = new Subject();
        linuxSubj.setId(UUID.randomUUID());
        linuxSubj.setName("Основы администрирования программно-аппаратных комплексов под управлением ОС Linux");
        subjectRepository.save(linuxSubj);

        // Saving Lecture and Schedules
        HashSet<LectureDTO> lectures = Utils.getLectures(bigDockerSubj, linuxSubj);
        int month = 1;
        int day = 8;
        LocalDateTime dateTime = LocalDateTime.of(2021, month, day, 9, 0, 0, 0);
        for (LectureDTO elem : lectures) {
            saveLecture(elem);

            Schedule schedule = new Schedule();
            schedule.setId(UUID.randomUUID());
            schedule.setLecture(LectureMapper.dtoToPostgreEntity(elem));
            schedule.setGroups(groups);
            schedule.setDate(dateTime);
            day += 10;
            if (day >= 28) {
                month += 1;
                day = 8;
            }
            dateTime = LocalDateTime.of(2021, month, day, 9, 0, 0, 0);
            saveSchedule(schedule);

            for (StudentDTO student : students) {
                Visit visit = Utils.getRandomVisit(schedule, student);
                saveVisit(visit);
            }
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
    public List<LectureElastic> findByTextEntry(String textEntry) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchPhraseQuery("text", textEntry))
                .build();
        return elasticsearchRestTemplate.search(searchQuery, LectureElastic.class)
                .getSearchHits()
                .stream()
                .map(SearchHit::getContent).collect(Collectors.toList());
    }

    @Transactional
    public void saveLecture(LectureDTO dto) {
        Lecture lecture = new Lecture(dto.getId(), dto.getName(), dto.getSubject());
        LectureElastic lectureElastic = new LectureElastic(dto.getId().toString(), dto.getText());
        lectureRepository.save(lecture);
        lectureElasticRepository.save(lectureElastic);
    }

    @Transactional(readOnly = true)
    public List<LectureElastic> getAllLectures() {
        List<LectureElastic> lst = new ArrayList<>();
        lectureElasticRepository.findAll().forEach(lst::add);
        return lst;
    }

    /***
     *
     * STUDENT
     */
    @Transactional
    public void saveStudent(StudentDTO student) {
        studentRepository.save(new Student(student.getId(), student.getGroup()));
        redisRepository.save(new StudentRedis(student.getId(), student.getName()));
        studentMongoRepository.save(new StudentMongo(student.getId(), student.getName()));
    }

    @Transactional(readOnly = true)
    public Map<String, StudentRedis> getAllStudents() {
        return redisRepository.findAllStudents();
    }

    /***
     *
     * GROUP
     */
    @Transactional
    public void saveGroup(Group group) {
        Set<StudentMongo> studentMongos = new HashSet<>();
        for (Student student : group.getStudents()) {
            studentMongos.add(new StudentMongo(student.getId(), student.getGroupEntity().getGroupCode()));
        }
        groupMongoRepository.save(new GroupMongo(group.getId(), group.getGroupCode(), studentMongos));
        groupRepository.save(group);
    }

    @Transactional(readOnly = true)
    public List<GroupMongo> getAllGroupsMongo() {
        return groupMongoRepository.findAll();
    }

    @Transactional
    public Group saveGroup1(Group group) {
        GroupMongo groupMongo = new GroupMongo(group.getId(), group.getGroupCode(), null);
        groupMongoRepository.save(groupMongo);
//        group = groupRepository.save(group);
//        for (StudentDTO student : studentDTOS) {
//            StudentHash studentHash = new StudentHash(student.getId(), student.getName());
//            redisRepository.save(studentHash);
//        }
        return group;
    }

    public List<GroupMongo> getAllGroups1() {
        return groupMongoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
}
