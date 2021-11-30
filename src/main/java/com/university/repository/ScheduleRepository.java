package com.university.repository;

import com.university.entity.Lecture;
import com.university.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {

    List<Schedule> findByLectureIdAndDateBetween(UUID lectureId, LocalDateTime from, LocalDateTime to);
}
