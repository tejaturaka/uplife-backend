package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "https://uplife-frontend.vercel.app")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerOrUpdate(@RequestBody User user) {
        try {
            // Generate ID only if it's a NEW registration
            if (user.getId() == null || user.getId().trim().isEmpty()) {
                
                String role = user.getRole();
                List<User> allUsers = userRepository.findAll();

                // ---------------------------------------------------------
                // LOGIC 1: AGENTS -> Location Based ID (e.g., TEHYAM0001)
                // ---------------------------------------------------------
                if ("agent".equalsIgnoreCase(role)) {
                    String stateCode = getAbbreviation(user.getState());
                    String distCode = getAbbreviation(user.getDist());
                    String mandalCode = getAbbreviation(user.getMandal());
                    
                    String idPrefix = (stateCode + distCode + mandalCode).toUpperCase();
                    int maxSequence = 0;

                    for (User u : allUsers) {
                        if (u.getId() != null && u.getId().startsWith(idPrefix)) {
                            try {
                                String seqStr = u.getId().substring(6); 
                                int seq = Integer.parseInt(seqStr);
                                if (seq > maxSequence) maxSequence = seq;
                            } catch (Exception e) {}
                        }
                    }
                    user.setId(idPrefix + String.format("%04d", maxSequence + 1));
                } 
                
                // ---------------------------------------------------------
                // LOGIC 2: CUSTOMERS -> Pure 10-Digit Sequence (e.g., 0000000001)
                // ---------------------------------------------------------
                else {
                    long maxNumericId = 0;
                    for (User u : allUsers) {
                        if (u.getId() != null) {
                            try {
                                // Only count IDs that are purely numbers
                                long currentId = Long.parseLong(u.getId());
                                if (currentId > maxNumericId) {
                                    maxNumericId = currentId;
                                }
                            } catch (NumberFormatException e) {
                                // Ignore Agent IDs (they have letters)
                            }
                        }
                    }
                    user.setId(String.format("%010d", maxNumericId + 1));
                }
            }

            User saved = userRepository.save(user);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // Helper for Agent Abbreviations
    private String getAbbreviation(String input) {
        if (input == null || input.trim().isEmpty()) return "XX";
        input = input.trim().toUpperCase();
        String[] parts = input.split(" ");
        if (parts.length > 1) return "" + parts[0].charAt(0) + parts[1].charAt(0);
        if (input.length() >= 2) return input.substring(0, 2);
        return input + "X";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User creds) {
        return userRepository.findById(creds.getId())
                .filter(u -> u.getPassword().equals(creds.getPassword()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}