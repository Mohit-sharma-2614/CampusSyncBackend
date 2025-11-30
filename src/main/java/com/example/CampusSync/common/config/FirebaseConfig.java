package com.example.CampusSync.common.config;

// import com.google.auth.oauth2.GoogleCredentials;
// import com.google.firebase.FirebaseApp;
// import com.google.firebase.FirebaseOptions;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import java.io.IOException;
// import java.io.InputStream;
// import java.io.FileInputStream;
// import java.io.FileNotFoundException;

// @Configuration
// public class FirebaseConfig {

//     @org.springframework.beans.factory.annotation.Value("${firebase.config.path:firebase-service-account.json}")
//     private String firebaseConfigPath;

//     @Bean
//     public FirebaseApp firebaseApp() throws IOException {
//         InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream(firebaseConfigPath);

//         if (serviceAccount == null) {
//             // Try to load from file system if not in classpath
//             try {
//                 serviceAccount = new FileInputStream(firebaseConfigPath);
//             } catch (FileNotFoundException e) {
//                 System.err.println("Firebase Service Account JSON not found at: " + firebaseConfigPath);
//                 // Return null or throw exception depending on strictness. 
//                 // Returning null might break injection points, so let's throw a more descriptive error or handle it.
//                 // For now, let's keep the exception but make it clear.
//                 throw new RuntimeException("Firebase Service Account JSON not found! Please place '" + firebaseConfigPath + "' in src/main/resources or configure 'firebase.config.path'.");
//             }
//         }

//         FirebaseOptions options = FirebaseOptions.builder()
//                 .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                 .build();

//         // Check if already initialized to avoid errors during hot-reloads
//         if (FirebaseApp.getApps().isEmpty()) {
//             return FirebaseApp.initializeApp(options);
//         }
//         return FirebaseApp.getInstance();
//     }
// }