package com.example.CampusSync.subject.service;

import com.example.CampusSync.subject.dto.SubjectDTO;
import com.example.CampusSync.subject.model.Subject;

import java.util.List;

public interface SubjectService {
    List<SubjectDTO> getAllSubjects();
    SubjectDTO getSubject(Long subjectId);
    SubjectDTO createSubject(Subject subject);
    SubjectDTO updateSubject(Subject subject);
    void deleteSubject(Long subjectId);
}
