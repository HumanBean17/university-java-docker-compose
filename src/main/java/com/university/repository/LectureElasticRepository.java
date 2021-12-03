package com.university.repository;

import com.university.entity.LectureElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureElasticRepository extends ElasticsearchRepository<LectureElastic, String> {

    List<LectureElastic> findLectureTextByTextContains(String textEntry);
}
