package com.example.CampusSync.enrollment.dto;

import com.example.CampusSync.enrollment.model.Enrollment;
import com.example.CampusSync.student.dto.StudentDTO;
import com.example.CampusSync.student.entity.Student;
import com.example.CampusSync.subject.dto.SubjectDTO;
import com.example.CampusSync.subject.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EnrollmentDTO {
    private Long id;
    private StudentDTO student;
    private SubjectDTO subject;

    public EnrollmentDTO(Enrollment enrollment){
        this.id = enrollment.getId();
        this.student = new StudentDTO(enrollment.getStudent());
        this.subject = new SubjectDTO(enrollment.getSubject());
    }
}
