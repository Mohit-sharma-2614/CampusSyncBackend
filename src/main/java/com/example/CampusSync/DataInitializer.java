//package com.example.CampusSync;
//
//import com.example.CampusSync.attendance.model.Attendance;
//import com.example.CampusSync.attendance.repository.AttendanceRepository;
//import com.example.CampusSync.attendance_token.model.AttendanceToken;
//import com.example.CampusSync.attendance_token.repository.AttendanceTokenRepository;
//import com.example.CampusSync.department.model.Department;
//import com.example.CampusSync.department.repository.DepartmentRepository;
//import com.example.CampusSync.enrollment.model.Enrollment;
//import com.example.CampusSync.enrollment.repository.EnrollmentRepository;
//import com.example.CampusSync.student.entity.Student;
//import com.example.CampusSync.student.repository.StudentRepository;
//import com.example.CampusSync.subject.model.Subject;
//import com.example.CampusSync.subject.repository.SubjectRepository;
//import com.example.CampusSync.teacher.model.Teacher;
//import com.example.CampusSync.teacher.repository.TeacherRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.sql.Timestamp;
//import java.util.UUID;
//import java.time.Instant;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    private final StudentRepository studentRepository;
//    private final EnrollmentRepository enrollmentRepository;
//    private final DepartmentRepository departmentRepository;
//    private final AttendanceTokenRepository attendanceTokenRepository;
//    private final AttendanceRepository attendanceRepository;
//    private final SubjectRepository subjectRepository;
//    private final TeacherRepository teacherRepository;
//
//    public DataInitializer(StudentRepository studentRepository, EnrollmentRepository enrollmentRepository,
//                           DepartmentRepository departmentRepository, AttendanceTokenRepository attendanceTokenRepository,
//                           AttendanceRepository attendanceRepository, SubjectRepository subjectRepository,
//                           TeacherRepository teacherRepository) {
//        this.studentRepository = studentRepository;
//        this.enrollmentRepository = enrollmentRepository;
//        this.departmentRepository = departmentRepository;
//        this.attendanceTokenRepository = attendanceTokenRepository;
//        this.attendanceRepository = attendanceRepository;
//        this.subjectRepository = subjectRepository;
//        this.teacherRepository = teacherRepository;
//    }
//
//    @Override
//    public void run(String... args) {
//        // Insert Departments
//        Department dept1 = new Department();
//        dept1.setName("Computer Science");
//        Department dept2 = new Department();
//        dept2.setName("Mathematics");
////        departmentRepository.save(dept1);
////        departmentRepository.save(dept2);
//
//        // Insert Teachers
//        Teacher teacher1 = new Teacher();
//        teacher1.setName("Dr. Smith");
//        teacher1.setEmail("smith@university.edu");
//        teacher1.setPassword("pass123");
//        teacher1.setDepartment(dept1);
//        teacher1.setCreatedAt(LocalDateTime.now());
//
//        Teacher teacher2 = new Teacher();
//        teacher2.setName("Prof. Jones");
//        teacher2.setEmail("jones@university.edu");
//        teacher2.setPassword("pass456");
//        teacher2.setDepartment(dept2);
//        teacher2.setCreatedAt(LocalDateTime.now());
//
//        teacherRepository.save(teacher1);
//        teacherRepository.save(teacher2);
//
//        // Insert Students
//        Student student1 = new Student();
//        student1.setName("Alice Brown");
//        student1.setStudent_uid("S001");
//        student1.setEmail("alice@university.edu");
//        student1.setPassword("alice123");
//        student1.setSemester(3);
//        student1.setDepartment(dept1);
//        student1.setCreated_at(LocalDateTime.now());
//
//        Student student2 = new Student();
//        student2.setName("Bob Wilson");
//        student2.setStudent_uid("S002");
//        student2.setEmail("bob@university.edu");
//        student2.setPassword("bob456");
//        student2.setSemester(5);
//        student2.setDepartment(dept1);
//        student2.setCreated_at(LocalDateTime.now());
//
//        Student student3 = new Student();
//        student3.setName("Carol Davis");
//        student3.setStudent_uid("S003");
//        student3.setEmail("carol@university.edu");
//        student3.setPassword("carol789");
//        student3.setSemester(4);
//        student3.setDepartment(dept2);
//        student3.setCreated_at(LocalDateTime.now());
//
//        studentRepository.save(student1);
//        studentRepository.save(student2);
//        studentRepository.save(student3);
//
//        // Insert Subjects
//        Subject subject1 = new Subject();
//        subject1.setName("Data Structures");
//        subject1.setCode("CS101");
//        subject1.setSemester("Fall 2025");
//        subject1.setDepartment(dept1);
//        subject1.setTeacher(teacher1);
//        subject1.setCreatedAt(LocalDateTime.now());
//
//        Subject subject2 = new Subject();
//        subject2.setName("Calculus I");
//        subject2.setCode("MATH101");
//        subject2.setSemester("Fall 2025");
//        subject2.setDepartment(dept2);
//        subject2.setTeacher(teacher2);
//        subject2.setCreatedAt(LocalDateTime.now());
//
//        subjectRepository.save(subject1);
//        subjectRepository.save(subject2);
//
//        // Insert Enrollments
//        Enrollment enrollment1 = new Enrollment();
//        enrollment1.setStudent(student1);
//        enrollment1.setSubject(subject1);
//        enrollment1.setCreatedAt(LocalDateTime.now());
//
//        Enrollment enrollment2 = new Enrollment();
//        enrollment2.setStudent(student2);
//        enrollment2.setSubject(subject1);
//        enrollment2.setCreatedAt(LocalDateTime.now());
//
//        Enrollment enrollment3 = new Enrollment();
//        enrollment3.setStudent(student3);
//        enrollment3.setSubject(subject2);
//        enrollment3.setCreatedAt(LocalDateTime.now());
//
//        enrollmentRepository.save(enrollment1);
//        enrollmentRepository.save(enrollment2);
//        enrollmentRepository.save(enrollment3);
//
////        // Insert Attendance Tokens
////        AttendanceToken token1 = new AttendanceToken();
////        token1.setToken(UUID.randomUUID());
////        token1.setSubject(subject1);
////        token1.setTeacher(teacher1);
////        token1.setGeneratedAt(Timestamp.from(Instant.now()));
////        token1.setExpiresAt(Timestamp.from(Instant.now().plusSeconds(3600)));
////
////        AttendanceToken token2 = new AttendanceToken();
////        token2.setToken(UUID.randomUUID());
////        token2.setSubject(subject2);
////        token2.setTeacher(teacher2);
////        token2.setGeneratedAt(Timestamp.from(Instant.now()));
////        token2.setExpiresAt(Timestamp.from(Instant.now().plusSeconds(3600)));
////
////        attendanceTokenRepository.save(token1);
////        attendanceTokenRepository.save(token2);
//
//        // Insert Attendance
//        Attendance attendance1 = new Attendance();
//        attendance1.setStudent(student1);
//        attendance1.setSubject(subject1);
//        attendance1.setDate(LocalDate.now());
//        attendance1.setStatus("Present");
//        attendance1.setCreatedAt(LocalDateTime.now());
//
//        Attendance attendance2 = new Attendance();
//        attendance2.setStudent(student2);
//        attendance2.setSubject(subject1);
//        attendance2.setDate(LocalDate.now());
//        attendance2.setStatus("Absent");
//        attendance2.setCreatedAt(LocalDateTime.now());
//
//        Attendance attendance3 = new Attendance();
//        attendance3.setStudent(student3);
//        attendance3.setSubject(subject2);
//        attendance3.setDate(LocalDate.now());
//        attendance3.setStatus("Present");
//        attendanceRepository.save(attendance1);
//        attendanceRepository.save(attendance2);
//        attendanceRepository.save(attendance3);
//    }
//}