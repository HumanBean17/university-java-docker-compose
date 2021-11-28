package com.university.repository;

import com.university.entity.Lecture;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LectureRepository extends ElasticsearchRepository<Lecture, String> {


}
