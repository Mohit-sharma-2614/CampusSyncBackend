package com.example.CampusSync.common.service;


// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.messaging.simp.SimpMessagingTemplate;
// import org.springframework.stereotype.Service;

// import com.example.CampusSync.common.model.Notice;
// import com.example.CampusSync.common.repository.NoticeRepository;

// import java.sql.Timestamp;
// import java.time.Instant;
// import java.time.LocalDateTime;
// import java.util.List;

// @Service
// public class NoticeService {

//     @Autowired
//     private NoticeRepository noticeRepository;

//     @Autowired
//     private SimpMessagingTemplate messagingTemplate;

//     public Notice publishNotice(Notice notice) {
       
//         // 1. Set metadata
//         notice.setTimestamp(Timestamp.from(Instant.now()));

//         // 2. Persist to Database (Crucial for Offline Users)
//         Notice savedNotice = noticeRepository.save(notice);

//         // 3. Push to RabbitMQ via STOMP
//         if (notice.getScope() == Notice.NoticeScope.COLLEGE) {
//             // Broadcast to everyone
//             messagingTemplate.convertAndSend("/topic/college", savedNotice);
//         } else if (notice.getScope() == Notice.NoticeScope.DEPARTMENT) {
//             // Broadcast to specific department channel
//             // Example: /topic/dept.CS
//             String destination = "/topic/dept." + notice.getTargetDepartment();
//             messagingTemplate.convertAndSend(destination, savedNotice);
//         }

//         return savedNotice;
//     }

//     // Logic for fetching history when app opens (Offline recovery)
//     public List<Notice> getUnreadNotices(String studentDept, LocalDateTime lastSyncTime) {
//         // Fetch Global notices + Department notices created after lastSyncTime
//         return noticeRepository.findNoticesForStudent(studentDept, lastSyncTime);
//     }
// }
