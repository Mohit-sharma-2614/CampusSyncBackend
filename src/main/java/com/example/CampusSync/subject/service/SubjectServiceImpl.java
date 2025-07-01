package com.example.CampusSync.subject.service;

import com.example.CampusSync.common.exceptions.ResourceAlreadyExistException;
import com.example.CampusSync.common.exceptions.ResourceNotFoundException;
import com.example.CampusSync.subject.dto.SubjectDTO;
import com.example.CampusSync.subject.model.Subject;
import com.example.CampusSync.subject.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@ComponentScan
public class SubjectServiceImpl implements SubjectService{

    @Autowired
    SubjectRepository subjectRepository;


    @Override
    public List<SubjectDTO> getAllSubjects() {
        List<Subject> subject = subjectRepository.findAll();
        return subject.stream()
                .map(SubjectDTO::new)
                .toList();
    }

    @Override
    public SubjectDTO getSubject(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(()->new ResourceNotFoundException("Subject not found with ID: "+subjectId));
        return new SubjectDTO(subject);
    }

    @Override
    public SubjectDTO createSubject(Subject subject) {

        subject.setCreatedAt(LocalDateTime.now());

        Subject s = subjectRepository.save(subject);
        return new SubjectDTO(s);
    }

    @Override
    public SubjectDTO updateSubject(Subject subject) {

        Long subjectId = subject.getId();

        if(!subjectRepository.existsById(subjectId)){
            throw new ResourceNotFoundException("Subject not found with ID: "+subjectId);
        }

        Subject s = subjectRepository.saveAndFlush(subject);
        return new SubjectDTO(s);
    }

    @Override
    public void deleteSubject(Long subjectId) {


        if(!subjectRepository.existsById(subjectId)){
            throw new ResourceNotFoundException("Subject not found with ID: "+subjectId);
        }

        subjectRepository.deleteById(subjectId);
    }
}