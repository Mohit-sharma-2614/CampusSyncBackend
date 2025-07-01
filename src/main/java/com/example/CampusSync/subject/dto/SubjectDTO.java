package com.example.CampusSync.subject.dto;

import com.example.CampusSync.department.dto.DepartmentDTO;
import com.example.CampusSync.student.entity.Student;
import com.example.CampusSync.subject.model.Subject;
import com.example.CampusSync.teacher.dto.TeacherDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectDTO {

    private Long id;
    private String name;
    private String code;
    private String semester;
    private DepartmentDTO department;
    private TeacherDTO teacher;

    public SubjectDTO(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.code = subject.getCode();
        this.semester = subject.getSemester();
        this.department = new DepartmentDTO(subject.getDepartment());
        this.teacher = new TeacherDTO(subject.getTeacher());
    }

}
