package com.example.CampusSync.common.model;

// import jakarta.persistence.*;
// import lombok.Data;

// import java.sql.Timestamp;

// @Entity
// @Data
// @Table(name = "notices")
// public class Notice {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String title;
//     private String content;

//     @Enumerated(EnumType.STRING)
//     private NoticeScope scope; // COLLEGE or DEPARTMENT

//     private String targetDepartment; // e.g., "CS", "MECH" (Null if scope is COLLEGE)
    
//     private String authorName;
//     private Timestamp timestamp;

//     public enum NoticeScope {
//         COLLEGE,
//         DEPARTMENT
//     }
// }