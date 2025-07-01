package com.example.CampusSync.attendance_token.dto;

import com.example.CampusSync.attendance_token.model.AttendanceToken;
import com.example.CampusSync.subject.dto.SubjectDTO;
import com.example.CampusSync.subject.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AttendanceTokenDTO {
    private UUID token;
    private SubjectDTO subject;
    private Timestamp generatedAt;
    private Timestamp expiresAt;

    public AttendanceTokenDTO(AttendanceToken attendanceToken){
        this.token = attendanceToken.getToken();
        this.subject = new SubjectDTO(attendanceToken.getSubject());
        this.generatedAt = attendanceToken.getGeneratedAt();
        this.expiresAt = attendanceToken.getExpiresAt();
    }
}
