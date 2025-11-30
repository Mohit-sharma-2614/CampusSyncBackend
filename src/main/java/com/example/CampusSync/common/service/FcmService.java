package com.example.CampusSync.common.service;

// import com.google.firebase.messaging.FirebaseMessaging;
// import com.google.firebase.messaging.Message;
// import com.google.firebase.messaging.Notification;
// import org.springframework.stereotype.Service;

// @Service
// public class FcmService {

//     public void sendNotificationToTopic(String topic, String title, String body, Long noticeId) {
//         // Create the notification payload
//         Notification notification = Notification.builder()
//                 .setTitle(title)
//                 .setBody(body)
//                 .build();

//         // Build the message
//         Message message = Message.builder()
//                 .setTopic(topic) // e.g., "college" or "dept_CS"
//                 .setNotification(notification)
//                 .putData("noticeId", noticeId.toString()) // Custom data payload for click action
//                 .putData("click_action", "OPEN_NOTICE_ACTIVITY")
//                 .build();

//         try {
//             // Send asynchronously
//             String response = FirebaseMessaging.getInstance().send(message);
//             System.out.println("Sent message to topic: " + topic + ", response: " + response);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }