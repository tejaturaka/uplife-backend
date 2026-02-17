package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerOrUpdate(@RequestBody User user) {
        try {
            // REQUIREMENT: Generate 10-digit ID if ID is missing
            if (user.getId() == null || user.getId().trim().isEmpty()) {
                
                // 1. Generate Prefix (2 chars from State, Dist, Mandal)
                String stateCode = getAbbreviation(user.getState());
                String distCode = getAbbreviation(user.getDist());
                String mandalCode = getAbbreviation(user.getMandal());
                
                // Example: Telangana, Hyderabad, Amberpet -> TEHYAM
                String idPrefix = (stateCode + distCode + mandalCode).toUpperCase();
                
                // 2. Find Max Sequence for this specific location prefix
                List<User> allUsers = userRepository.findAll();
                int maxSequence = 0;

                for (User u : allUsers) {
                    if (u.getId() != null && u.getId().startsWith(idPrefix)) {
                        try {
                            // Get last 4 characters
                            String seqStr = u.getId().substring(6); 
                            int seq = Integer.parseInt(seqStr);
                            if (seq > maxSequence) {
                                maxSequence = seq;
                            }
                        } catch (Exception e) {
                            // Ignore IDs that don't match format
                        }
                    }
                }

                // 3. Generate ID: Prefix + 4-digit Sequence
                // Example: TEHYAM0001
                String nextId = idPrefix + String.format("%04d", maxSequence + 1);
                user.setId(nextId);
            }

            User saved = userRepository.save(user);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // Helper to get first 2 letters safely
    private String getAbbreviation(String input) {
        if (input == null || input.trim().isEmpty()) return "XX";
        input = input.trim().toUpperCase();
        
        // Handle "Andhra Pradesh" -> "AP"
        String[] parts = input.split(" ");
        if (parts.length > 1) {
            return "" + parts[0].charAt(0) + parts[1].charAt(0);
        }
        
        // Handle "Telangana" -> "TE"
        if (input.length() >= 2) {
            return input.substring(0, 2);
        }
        
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