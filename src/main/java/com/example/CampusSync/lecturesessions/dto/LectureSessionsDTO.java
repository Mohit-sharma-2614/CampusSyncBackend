package com.example.CampusSync.lecturesessions.dto;

import com.example.CampusSync.lecturesessions.model.LectureSessions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LectureSessionsDTO {
    private Long id;
    private Long courseOfferingId;
    private Date sessionDate;
    private Timestamp startTime;
    private Timestamp endTime;
    private String room;
    private String topic;

    public LectureSessionsDTO(LectureSessions lectureSessions) {
        if (lectureSessions != null) {
            this.id = lectureSessions.getId();
            if (lectureSessions.getCourseOfferings() != null) {
                this.courseOfferingId = lectureSessions.getCourseOfferings().getId();
            }
            this.sessionDate = lectureSessions.getSessionDate();
            this.startTime = lectureSessions.getStartTime();
            this.endTime = lectureSessions.getEndTime();
            this.room = lectureSessions.getRoom();
            this.topic = lectureSessions.getTopic();
        }
    }
}
