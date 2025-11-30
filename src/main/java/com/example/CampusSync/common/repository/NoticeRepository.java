package com.example.CampusSync.common.repository;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;

// import com.example.CampusSync.common.model.Notice;

// import java.time.LocalDateTime;
// import java.util.List;

// public interface NoticeRepository extends JpaRepository<Notice, Long> {

//     @Query("SELECT n FROM Notice n WHERE " +
//            "(n.scope = 'COLLEGE' OR (n.scope = 'DEPARTMENT' AND n.targetDepartment = :dept)) " +
//            "AND n.timestamp > :lastSync")
//     List<Notice> findNoticesForStudent(@Param("dept") String dept, 
//                                        @Param("lastSync") LocalDateTime lastSync);
// }