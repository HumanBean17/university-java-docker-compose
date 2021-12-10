package com.university.service;

import com.university.dto.*;
import com.university.entity.*;
import com.university.entity.elastic.LectureElastic;
import com.university.entity.mongo.CourseMongo;
import com.university.entity.mongo.LectureMongo;
import com.university.entity.mongo.SpecialityMongo;
import com.university.entity.mongo.SubjectMongo;
import com.university.entity.neo4j.LectureNeo;
import com.university.entity.StudentRedis;
import com.university.entity.neo4j.ScheduleNeo;
import com.university.entity.neo4j.VisitNeo;
import com.university.mapper.*;
import com.university.repository.*;
import com.university.repository.elastic.LectureElasticRepository;
import com.university.repository.mongo.SpecialityMongoRepository;
import com.university.repository.neo4j.LectureNeoRepository;
import com.university.repository.neo4j.ScheduleNeoRepository;
import com.university.repository.neo4j.VisitNeoRepository;
import com.university.repository.redis.StudentRedisRepository;
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
    private final StudentRedisRepository studentRedisRepository;
    // Mongo
    private final SpecialityMongoRepository specialityMongoRepository;
    // Neo4j
    private final LectureNeoRepository lectureNeoRepository;
    private final VisitNeoRepository visitNeoRepository;
    private final ScheduleNeoRepository scheduleNeoRepository;

    // Postgres
    private final LectureRepository lectureRepository;
    private final GroupRepository groupRepository;
    private final VisitRepository visitRepository;
    private final ScheduleRepository scheduleRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final CourseRepository courseRepository;
    private final SpecialityRepository specialityRepository;

    @Transactional(readOnly = true)
    public LabTwoDTO labTwoQuery(FindDTO findDTO) {
        LabTwoDTO result = new LabTwoDTO();
        int max = -1;
        if (findDTO.getSemester() == 1) {
            findDTO.setFrom(LocalDateTime.of(findDTO.getYear(), 9, 1, 0, 0, 0));
            findDTO.setTo(LocalDateTime.of(findDTO.getYear(), 12, 31, 0, 0, 0));
        } else if (findDTO.getSemester() == 2) {
            findDTO.setFrom(LocalDateTime.of(findDTO.getYear(), 1, 1, 0, 0, 0));
            findDTO.setTo(LocalDateTime.of(findDTO.getYear(), 5, 31, 0,0,0));
        } else {
            throw new UnsupportedOperationException();
        }

        List<SpecialityMongo> specialities = specialityMongoRepository.findAll();
        Set<UUID> lectures = new HashSet<>();
        for (SpecialityMongo speciality : specialities) {
            for (CourseMongo course : speciality.getCourses()) {
                for (SubjectMongo subject : course.getSubjects()) {
                    Set<UUID> tmpLectures = subject.getLectures()
                            .stream()
                            .map(LectureMongo::getId)
                            .collect(Collectors.toSet());
                    lectures.addAll(tmpLectures);

                    List<Schedule> schedules = new LinkedList<>();
                    for (UUID id : tmpLectures) {
                        schedules.addAll(scheduleRepository.findAllByLectureIdAndDateBetween(id, findDTO.getFrom(), findDTO.getTo()));
                    }

                    for (Schedule schedule : schedules) {
                        int groupsSize = schedule.getGroups().size();
                        if (groupsSize > max) {
                            max = groupsSize;
                        }
                    }
                }
            }
        }

        result.setAudienceCapacity(max);
        return result;
    }

    @Transactional(readOnly = true)
    public LabOneDTO labOneQuery(FindDTO findDTO) {
//        Set<ScheduleNeo> schedules = new HashSet<>();
        Set<VisitNeo> visits = new HashSet<>();

        List<UUID> tmpLectures = findByTextEntry(findDTO.getLecturePhrase()).
                stream()
                .map(elem -> UUID.fromString(elem.getId()))
                .collect(Collectors.toList());
        List<LectureNeo> lectures = lectureNeoRepository.findAllById(tmpLectures);

        for (LectureNeo lecture : lectures) {
            Set<ScheduleNeo> tmpSchedules = lecture.getSchedules()
                    .stream()
                    .filter(elem -> elem.getDate().isAfter(findDTO.getFrom()) && elem.getDate().isBefore(findDTO.getTo()))
                    .collect(Collectors.toSet());
//            schedules.addAll(tmpSchedules);
            for (ScheduleNeo schedule : tmpSchedules) {
                visits.addAll(schedule.getVisits());
            }
        }

        Map<String, Integer> visited = new HashMap<>();
        Map<String, Integer> unvisited = new HashMap<>();
        SortedMap<String, Integer> percentVisit = new TreeMap<>();
        for (VisitNeo visit : visits) {
            String id = visit.getStudent().toString();
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
            StudentRedis studentRedis = studentRedisRepository.findStudentById(elem.getKey());
            result.getStudents().add(
                    new StudentDTO(student.getId(), studentRedis.getName(), student.getGroupEntity(), student.getGroupEntity().getSpeciality(), elem.getValue()));
            if (i >= findDTO.getNumber()) {
                break;
            }
            i++;
        }
        result.setFrom(findDTO.getFrom());
        result.setTo(findDTO.getTo());
        result.setPhrase(findDTO.getLecturePhrase());
        return result;
    }

    @Transactional
    public String labOneData() {
        // Saving Specialities and Course
        Set<SpecialityDTO> specialities = new HashSet<>();
        SpecialityDTO speciality = new SpecialityDTO();
        speciality.setId(UUID.randomUUID());
        speciality.setName("Информационные системы и технологии");
        specialities.add(speciality);

        CourseDTO course = new CourseDTO();
        course.setId(UUID.randomUUID());
        course.setHours(90);
        course.setSpecialities(specialities);
        courseRepository.save(CourseMapper.dtoToPostgres(course));

        // Saving Subjects
        SubjectDTO bigDockerSubj = new SubjectDTO();
        bigDockerSubj.setCourse(course);
        bigDockerSubj.setId(UUID.randomUUID());
        bigDockerSubj.setName("Принципы построения, проектирования и эксплуатации информационных систем");
        subjectRepository.save(SubjectMapper.dtoToPostgres(bigDockerSubj));
        SubjectDTO linuxSubj = new SubjectDTO();
        linuxSubj.setCourse(course);
        linuxSubj.setId(UUID.randomUUID());
        linuxSubj.setName("Основы администрирования программно-аппаратных комплексов под управлением ОС Linux");
        subjectRepository.save(SubjectMapper.dtoToPostgres(linuxSubj));

        HashSet<LectureDTO> lectures = Utils.getLectures(SubjectMapper.dtoToPostgres(bigDockerSubj), SubjectMapper.dtoToPostgres(linuxSubj));
        bigDockerSubj.getLectures().addAll(lectures);
        linuxSubj.getLectures().addAll(lectures);
        course.getSubjects().addAll(Arrays.asList(bigDockerSubj, linuxSubj));
        speciality.getCourses().add(course);
        saveSpeciality(speciality);

        // Saving Group with Students
        Set<StudentDTO> students = new HashSet<>();
        Set<Group> groups = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            Group group = Utils.getRandomGroup();
            group.setSpeciality(SpecialityMapper.dtoToPostgres(speciality));
            saveGroup(group);
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

        // Saving Lecture and Schedules
        int month = 1;
        int day = 8;
        LocalDateTime dateTime = LocalDateTime.of(2021, month, day, 9, 0, 0, 0);
        for (LectureDTO lecture : lectures) {

            ScheduleDTO schedule = new ScheduleDTO();
            schedule.setId(UUID.randomUUID());
            schedule.setDate(dateTime);
            schedule.setLecture(lecture);
            schedule.setGroups(groups);
            Set<VisitDTO> visits = new HashSet<>();
            for (StudentDTO student : students) {
                VisitDTO visit = Utils.getRandomVisit(schedule, student);
                visits.add(visit);
                this.saveVisit(visit);
            }
            schedule.getVisits().addAll(visits);
            this.saveSchedule(schedule);

            lecture.getSchedules().add(schedule);
            this.saveLecture(lecture);

            day += 10;
            if (day >= 28) {
                month += 1;
                day = 8;
            }
            dateTime = LocalDateTime.of(2021, month, day, 9, 0, 0, 0);
        }
        return "OK";
    }

    /***
     * SPECIALITY
     */
    @Transactional
    public void saveSpeciality(SpecialityDTO specialityDTO) {
        Set<CourseDTO> courses = specialityDTO.getCourses();
        Set<SubjectDTO> subjects = new HashSet<>();
//        Set<LectureDTO> lectures = new HashSet<>();
        for (CourseDTO courseDTO : courses) {
            subjects.addAll(courseDTO.getSubjects());
//            courseRepository.save(CourseMapper.dtoToPostgres(courseDTO));
        }
        for (SubjectDTO subjectDTO : subjects) {
//            lectures.addAll(subjectDTO.getLectures());
//            subjectRepository.save(SubjectMapper.dtoToPostgres(subjectDTO));
        }
//        for (LectureDTO lectureDTO : lectures) {
//            saveLecture(lectureDTO);
//        }
        specialityRepository.save(SpecialityMapper.dtoToPostgres(specialityDTO));
        specialityMongoRepository.save(SpecialityMapper.dtoToMongo(specialityDTO));
    }

    @Transactional
    public List<SpecialityMongo> findAllMongoSpecialities() {
        return specialityMongoRepository.findAll();
    }

    /***
     * VISIT
     */
    @Transactional
    public void saveVisit(VisitDTO visit) {
        visitNeoRepository.save(VisitMapper.dtoToNeo(visit));
        visitRepository.save(VisitMapper.dtoToPostgres(visit));
    }

    /***
     * SCHEDULE
     */
    @Transactional
    public void saveSchedule(ScheduleDTO scheduleDTO) {
        scheduleNeoRepository.save(ScheduleMapper.dtoToNeo(scheduleDTO));
        scheduleRepository.save(ScheduleMapper.dtoToPostgres(scheduleDTO));
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
        Lecture lecture = LectureMapper.dtoToPostgres(dto);
        LectureElastic lectureElastic = LectureMapper.dtoToElastic(dto);
        LectureNeo lectureNeo = LectureMapper.dtoToNeo(dto);
        lectureRepository.save(lecture);
        lectureNeoRepository.save(lectureNeo);
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
        studentRedisRepository.save(new StudentRedis(student.getId(), student.getName()));
    }

    @Transactional(readOnly = true)
    public Map<String, StudentRedis> getAllStudents() {
        return studentRedisRepository.findAllStudents();
    }

    /***
     *
     * GROUP
     */
    @Transactional
    public void saveGroup(Group group) {
        groupRepository.save(group);
    }

//    @Transactional(readOnly = true)
//    public List<GroupMongo> getAllGroupsMongo() {
//        return groupMongoRepository.findAll();
//    }

    @Transactional(readOnly = true)
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
}
