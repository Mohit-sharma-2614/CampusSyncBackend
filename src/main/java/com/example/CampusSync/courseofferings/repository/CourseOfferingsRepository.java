package com.example.CampusSync.courseofferings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.CampusSync.courseofferings.model.CourseOfferings;

@Repository
public interface CourseOfferingsRepository extends JpaRepository<CourseOfferings, Long> {
    List<CourseOfferings> findByTeacherId(Long teacherId);
    List<CourseOfferings> findBySubjectIdAndTeacherIdAndSemester(Long subjectId, Long teacherId, Integer semester);
    List<CourseOfferings> findBySubjectIdAndTeacherId(Long subjectId, Long teacherId);
}
