package com.example.CampusSync.lecturesessions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CampusSync.lecturesessions.model.LectureSessions;

@Repository
public interface LectureSessionsRepository extends JpaRepository<LectureSessions, Long> {
}
