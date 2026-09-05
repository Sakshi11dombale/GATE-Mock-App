package com.gate.mocktest.database.dao;

import androidx.room.*;
import com.gate.mocktest.database.entities.Question;
import java.util.List;

@Dao
public interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Question> q);

    @Query("SELECT * FROM questions WHERE (branch=:branch OR (:branch='CSE' AND branch='CS') OR (:branch='ECE' AND branch='EC')) AND branch!='ALL' ORDER BY RANDOM() LIMIT :limit")
    List<Question> getTechnicalQuestions(String branch, int limit);

    @Query("SELECT * FROM questions WHERE branch='ALL' AND subject='General Aptitude' ORDER BY RANDOM() LIMIT :limit")
    List<Question> getAptitudeQuestions(int limit);

    @Query("SELECT * FROM questions WHERE subject='Engineering Mathematics' AND (branch=:branch OR (:branch='CSE' AND branch='CS') OR (:branch='ECE' AND branch='EC')) ORDER BY RANDOM() LIMIT :limit")
    List<Question> getMathQuestions(String branch, int limit);

    @Query("SELECT * FROM questions WHERE (branch=:branch OR (:branch='CSE' AND branch='CS') OR (:branch='ECE' AND branch='EC')) AND branch!='ALL' ORDER BY RANDOM() LIMIT :limit")
    List<Question> getMockTestQuestions(String branch, int limit);

    @Query("SELECT * FROM questions WHERE (subject=:subject OR topic=:subject OR subject=:altSubject OR topic=:altSubject OR subject LIKE '%' || :subject || '%' OR topic LIKE '%' || :subject || '%') AND (branch=:branch OR (:branch='CSE' AND branch='CS') OR (:branch='ECE' AND branch='EC')) AND branch!='ALL' ORDER BY RANDOM() LIMIT :limit")
    List<Question> getTopicQuestions(String subject, String altSubject, String branch, int limit);

    @Query("SELECT * FROM questions WHERE isPYQ=1 AND year=:year AND (branch=:branch OR (:branch='CSE' AND branch='CS') OR (:branch='ECE' AND branch='EC') OR (branch='ALL' AND subject='General Aptitude')) ORDER BY subject")
    List<Question> getPYQByYear(int year, String branch);

    @Query("SELECT * FROM questions WHERE isPYQ=1 AND (branch=:branch OR (:branch='CSE' AND branch='CS') OR (:branch='ECE' AND branch='EC')) ORDER BY year DESC, subject")
    List<Question> getAllPYQ(String branch);

    @Query("SELECT DISTINCT subject FROM questions WHERE branch=:branch OR (:branch='CSE' AND branch='CS') OR (:branch='ECE' AND branch='EC') ORDER BY subject")
    List<String> getSubjectsByBranch(String branch);

    @Query("SELECT DISTINCT year FROM questions WHERE isPYQ=1 AND (branch=:branch OR (:branch='CSE' AND branch='CS') OR (:branch='ECE' AND branch='EC') OR (branch='ALL' AND subject='General Aptitude')) ORDER BY year DESC")
    List<Integer> getPYQYears(String branch);


    @Query("SELECT COUNT(*) FROM questions WHERE (subject=:subject OR topic=:subject OR subject=:altSubject OR topic=:altSubject OR subject LIKE '%' || :subject || '%' OR topic LIKE '%' || :subject || '%') AND (branch=:branch OR (:branch='CSE' AND branch='CS') OR (:branch='ECE' AND branch='EC')) AND branch!='ALL'")
    int getTopicQuestionCount(String subject, String altSubject, String branch);

    @Query("SELECT COUNT(*) FROM questions")
    int getTotalCount();
}
