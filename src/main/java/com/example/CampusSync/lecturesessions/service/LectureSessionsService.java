package com.example.CampusSync.lecturesessions.service;

import java.util.List;

import com.example.CampusSync.lecturesessions.dto.LectureSessionsDTO;
import com.example.CampusSync.lecturesessions.dto.LectureSessionsDetailsDTO;
import com.example.CampusSync.lecturesessions.dto.LectureSessionsInputDTO;

public interface LectureSessionsService {
    List<LectureSessionsDTO> getAllLectureSessions();
    LectureSessionsDTO getLectureSession(Long id);
    LectureSessionsDetailsDTO getLectureSessionDetails(Long id);
    LectureSessionsDTO createLectureSession(LectureSessionsInputDTO inputDTO);
    LectureSessionsDTO updateLectureSession(Long id, LectureSessionsInputDTO inputDTO);
    void deleteLectureSession(Long id);
}
