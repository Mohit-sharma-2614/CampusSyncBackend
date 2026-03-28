package com.example.CampusSync.courseofferings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.CampusSync.courseofferings.model.CourseOfferings;

@Repository
public interface CourseOfferingsRepository extends JpaRepository<CourseOfferings, Long> {
}
