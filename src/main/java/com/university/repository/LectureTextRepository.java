package com.university.repository;

import com.university.entity.LectureText;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureTextRepository extends ElasticsearchRepository<LectureText, String> {

    List<LectureText> findLectureTextByTextContains(String textEntry);
}
