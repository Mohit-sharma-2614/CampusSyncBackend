package com.example.CampusSync.attendance.repository;


import com.example.CampusSync.attendance.model.Attendance;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@ComponentScan
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
}
