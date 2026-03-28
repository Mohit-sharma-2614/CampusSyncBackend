package com.example.CampusSync.subject.service;

import java.util.List;

import com.example.CampusSync.subject.dto.SubjectDTO;
import com.example.CampusSync.subject.dto.SubjectDetailsDTO;
import com.example.CampusSync.subject.dto.SubjectInputDTO;

public interface SubjectService {
    List<SubjectDTO> getAllSubjects();
    SubjectDTO getSubject(Long subjectId);
    SubjectDetailsDTO getSubjectDetails(Long subjectId);
    SubjectDTO createSubject(SubjectInputDTO subjectDto);
    SubjectDTO updateSubject(Long subjectId, SubjectInputDTO subjectDto);
    void deleteSubject(Long subjectId);
}
