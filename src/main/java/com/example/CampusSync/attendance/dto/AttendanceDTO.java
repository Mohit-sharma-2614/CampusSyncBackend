package com.example.CampusSync.attendance.dto;

import com.example.CampusSync.attendance.model.Attendance;
import com.example.CampusSync.student.dto.StudentDTO;
import com.example.CampusSync.subject.dto.SubjectDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AttendanceDTO {

    private Long id;
    private StudentDTO student;
    private SubjectDTO subject;
    private LocalDate date;
    private String status;

    public AttendanceDTO(Attendance attendance){
        this.id = attendance.getId();
        this.student = new StudentDTO(attendance.getStudent());
        this.subject = new SubjectDTO(attendance.getSubject());
        this.date = attendance.getDate();
        this.status = attendance.getStatus();
    }

}
