package com.example.CampusSync.lecturesessions.dto;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LectureSessionsInputDTO {
    private Long courseOfferingId;
    private Date sessionDate;
    private Timestamp startTime;
    private Timestamp endTime;
    private String room;
    private String topic;
}
