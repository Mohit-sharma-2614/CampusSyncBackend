package com.example.CampusSync.attendance_token.repository;

import com.example.CampusSync.attendance_token.model.AttendanceToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AttendanceTokenRepository extends JpaRepository<AttendanceToken, UUID> {

}
