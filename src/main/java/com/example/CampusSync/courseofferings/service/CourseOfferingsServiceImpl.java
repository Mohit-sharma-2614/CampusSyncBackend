package com.example.CampusSync.courseofferings.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.CampusSync.common.exceptions.ResourceNotFoundException;
import com.example.CampusSync.courseofferings.dto.CourseOfferingsDTO;
import com.example.CampusSync.courseofferings.dto.CourseOfferingsDetailsDTO;
import com.example.CampusSync.courseofferings.dto.CourseOfferingsInputDTO;
import com.example.CampusSync.courseofferings.model.CourseOfferings;
import com.example.CampusSync.courseofferings.repository.CourseOfferingsRepository;
import com.example.CampusSync.subject.model.Subject;
import com.example.CampusSync.subject.repository.SubjectRepository;
import com.example.CampusSync.teacher.model.Teacher;
import com.example.CampusSync.teacher.repository.TeacherRepository;

@Service
public class CourseOfferingsServiceImpl implements CourseOfferingsService {

    @Autowired
    private CourseOfferingsRepository repository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public List<CourseOfferingsDTO> getAllCourseOfferings() {
        return repository.findAll().stream().map(CourseOfferingsDTO::new).toList();
    }

    @Override
    public CourseOfferingsDTO getCourseOffering(Long id) {
        CourseOfferings co = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course Offering not found"));
        return new CourseOfferingsDTO(co);
    }

    @Override
    public CourseOfferingsDetailsDTO getCourseOfferingDetails(Long id) {
        CourseOfferings co = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course Offering not found"));
        return new CourseOfferingsDetailsDTO(co);
    }

    @Override
    @Transactional
    public CourseOfferingsDTO createCourseOffering(CourseOfferingsInputDTO inputDTO) {
        if (inputDTO.getSubjectId() == null) throw new IllegalArgumentException("Subject ID cannot be null");
        if (inputDTO.getTeacherId() == null) throw new IllegalArgumentException("Teacher ID cannot be null");

        Subject subject = subjectRepository.findById(inputDTO.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        Teacher teacher = teacherRepository.findById(inputDTO.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        CourseOfferings co = new CourseOfferings();
        co.setSubject(subject);
        co.setTeacher(teacher);
        co.setAcademicYear(inputDTO.getAcademicYear());
        co.setSemester(inputDTO.getSemester());
        co.setSection(inputDTO.getSection());

        return new CourseOfferingsDTO(repository.save(co));
    }

    @Override
    @Transactional
    public CourseOfferingsDTO updateCourseOffering(Long id, CourseOfferingsInputDTO inputDTO) {
        CourseOfferings co = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course Offering not found"));

        if (inputDTO.getSubjectId() == null) throw new IllegalArgumentException("Subject ID cannot be null");
        if (inputDTO.getTeacherId() == null) throw new IllegalArgumentException("Teacher ID cannot be null");

        Subject subject = subjectRepository.findById(inputDTO.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        Teacher teacher = teacherRepository.findById(inputDTO.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        co.setSubject(subject);
        co.setTeacher(teacher);
        co.setAcademicYear(inputDTO.getAcademicYear());
        co.setSemester(inputDTO.getSemester());
        co.setSection(inputDTO.getSection());

        return new CourseOfferingsDTO(repository.save(co));
    }

    @Override
    @Transactional
    public void deleteCourseOffering(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Course Offering not found");
        repository.deleteById(id);
    }

    @Override
    public List<CourseOfferingsDTO> courseOfferingsByTeacherId(Long teacherId) {
        if (!teacherRepository.existsById(teacherId)) {
            throw new ResourceNotFoundException("Teacher not found");
        }
        return repository.findByTeacherId(teacherId).stream().map(CourseOfferingsDTO::new).toList();
    }

    @Override
    public List<CourseOfferingsDTO> getCourseOfferingsBySubjectTeacherAndSemester(Long subjectId, Long teacherId, Integer semester) {
        return repository.findBySubjectIdAndTeacherIdAndSemester(subjectId, teacherId, semester)
                .stream().map(CourseOfferingsDTO::new).toList();
    }

    @Override
    public List<CourseOfferingsDTO> getCourseOfferingsBySubjectAndTeacher(Long subjectId, Long teacherId) {
        return repository.findBySubjectIdAndTeacherId(subjectId, teacherId)
                .stream().map(CourseOfferingsDTO::new).toList();
    }
}
