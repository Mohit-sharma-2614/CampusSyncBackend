package com.example.CampusSync.lecturesessions.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CampusSync.common.exceptions.ResourceNotFoundException;
import com.example.CampusSync.courseofferings.model.CourseOfferings;
import com.example.CampusSync.courseofferings.repository.CourseOfferingsRepository;
import com.example.CampusSync.lecturesessions.dto.LectureSessionsDTO;
import com.example.CampusSync.lecturesessions.dto.LectureSessionsDetailsDTO;
import com.example.CampusSync.lecturesessions.dto.LectureSessionsInputDTO;
import com.example.CampusSync.lecturesessions.model.LectureSessions;
import com.example.CampusSync.lecturesessions.repository.LectureSessionsRepository;

@Service
public class LectureSessionsServiceImpl implements LectureSessionsService {

    @Autowired
    private LectureSessionsRepository lectureSessionsRepository;

    @Autowired
    private CourseOfferingsRepository courseOfferingsRepository;

    @Override
    public List<LectureSessionsDTO> getAllLectureSessions() {
        return lectureSessionsRepository.findAll().stream()
                .map(LectureSessionsDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public LectureSessionsDTO getLectureSession(Long id) {
        LectureSessions session = lectureSessionsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lecture Session not found with ID: " + id));
        return new LectureSessionsDTO(session);
    }

    @Override
    public LectureSessionsDetailsDTO getLectureSessionDetails(Long id) {
        LectureSessions session = lectureSessionsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lecture Session not found with ID: " + id));
        return new LectureSessionsDetailsDTO(session);
    }

    @Override
    public List<LectureSessionsDTO> getLectureSessionsByCourseOfferingId(Long courseOfferingId) {
        List<LectureSessions> sessions = lectureSessionsRepository.findByCourseOfferingsId(courseOfferingId);
        return sessions.stream()
                .map(LectureSessionsDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public LectureSessionsDTO createLectureSession(LectureSessionsInputDTO inputDTO) {
        if (inputDTO.getCourseOfferingId() == null) {
            throw new IllegalArgumentException("Course Offering ID cannot be null");
        }
        CourseOfferings courseOfferings = courseOfferingsRepository.findById(inputDTO.getCourseOfferingId())
                .orElseThrow(() -> new ResourceNotFoundException("Course Offering not found with ID: " + inputDTO.getCourseOfferingId()));
        
        LectureSessions lectureSessions = new LectureSessions();
        lectureSessions.setCourseOfferings(courseOfferings);
        lectureSessions.setSessionDate(inputDTO.getSessionDate());
        lectureSessions.setStartTime(inputDTO.getStartTime());
        lectureSessions.setEndTime(inputDTO.getEndTime());
        lectureSessions.setRoom(inputDTO.getRoom());
        lectureSessions.setTopic(inputDTO.getTopic());
        lectureSessions.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        LectureSessions savedSession = lectureSessionsRepository.save(lectureSessions);
        return new LectureSessionsDTO(savedSession);
    }

    @Override
    public LectureSessionsDTO updateLectureSession(Long id, LectureSessionsInputDTO inputDTO) {
        if (id == null || !lectureSessionsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lecture Session not found with ID: " + id);
        }

        if (inputDTO.getCourseOfferingId() == null) {
            throw new IllegalArgumentException("Course Offering ID cannot be null");
        }
        CourseOfferings courseOfferings = courseOfferingsRepository.findById(inputDTO.getCourseOfferingId())
                .orElseThrow(() -> new ResourceNotFoundException("Course Offering not found with ID: " + inputDTO.getCourseOfferingId()));
        
        LectureSessions lectureSessions = lectureSessionsRepository.findById(id).orElseThrow();
        lectureSessions.setCourseOfferings(courseOfferings);
        if (inputDTO.getSessionDate() != null) {
            lectureSessions.setSessionDate(inputDTO.getSessionDate());
        }
        if (inputDTO.getStartTime() != null) {
            lectureSessions.setStartTime(inputDTO.getStartTime());
        }
        if (inputDTO.getEndTime() != null) {
            lectureSessions.setEndTime(inputDTO.getEndTime());
        }
        if (inputDTO.getRoom() != null) {
            lectureSessions.setRoom(inputDTO.getRoom());
        }
        if (inputDTO.getTopic() != null) {
            lectureSessions.setTopic(inputDTO.getTopic());
        }

        LectureSessions updatedSession = lectureSessionsRepository.saveAndFlush(lectureSessions);
        return new LectureSessionsDTO(updatedSession);
    }

    @Override
    public void deleteLectureSession(Long id) {
        if (!lectureSessionsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lecture Session not found with ID: " + id);
        }
        lectureSessionsRepository.deleteById(id);
    }
}
