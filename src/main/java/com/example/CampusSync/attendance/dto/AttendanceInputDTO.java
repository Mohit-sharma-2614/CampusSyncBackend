package com.example.CampusSync.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.UUID;

import com.example.CampusSync.attendance.model.AttendanceStatus;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceInputDTO {
    private Long enrollmentId;
    private Long lectureSessionId;
    private UUID tokenId;
    private AttendanceStatus status;
}
