package com.example.CampusSync.attendance_token.controller;


import com.example.CampusSync.attendance_token.dto.AttendanceTokenDTO;
import com.example.CampusSync.attendance_token.model.AttendanceToken;
import com.example.CampusSync.attendance_token.service.AttendanceTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/attendance_token")
public class AttendanceTokenController {
    @Autowired
    AttendanceTokenServiceImpl attendanceTokensService;

    @GetMapping("/all")
    public ResponseEntity<List<AttendanceTokenDTO>> getAllAttendanceTokens(){
        List<AttendanceTokenDTO> tokens = attendanceTokensService.getAllTokens();
        return ResponseEntity.ok(tokens);
    }

    @GetMapping
    public ResponseEntity<AttendanceTokenDTO> getAttendanceTokenById(
            @Param("tokenId") String tokenId
    ){
        try {
            AttendanceTokenDTO token = attendanceTokensService.getToken(UUID.fromString(tokenId));
            return ResponseEntity.ok(token);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<AttendanceTokenDTO> createToken(@RequestBody AttendanceToken attendanceToken) {
        AttendanceTokenDTO createdToken = attendanceTokensService.createToken(attendanceToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdToken); // 201 Created
    }

    @PutMapping
    public ResponseEntity<AttendanceTokenDTO> updateToken(@RequestBody AttendanceToken attendanceToken) {
        try {
            AttendanceTokenDTO updatedToken = attendanceTokensService.updateToken(attendanceToken);
            return ResponseEntity.ok(updatedToken); // 200 OK
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteToken(@RequestParam("tokenId") String tokenId) {
        try {
            attendanceTokensService.deleteToken(UUID.fromString(tokenId));
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
