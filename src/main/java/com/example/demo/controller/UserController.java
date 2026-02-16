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
            // REQUIREMENT: Sequential 10-digit ID for "user" role
            if ("user".equalsIgnoreCase(user.getRole())) {
                
                // Only generate if it's a new registration (ID is null or empty)
                if (user.getId() == null || user.getId().isEmpty()) {
                    List<User> allUsers = userRepository.findAll();
                    long maxNumericId = 0;

                    for (User u : allUsers) {
                        try {
                            // Convert existing string IDs to long to find the max
                            long currentId = Long.parseLong(u.getId());
                            if (currentId > maxNumericId) {
                                maxNumericId = currentId;
                            }
                        } catch (NumberFormatException e) {
                            // Ignore non-numeric IDs like '123' or 'GOGONO...'
                        }
                    }
                    
                    // Increment and format to 10 digits (e.g., 0000000001)
                    String nextSequentialId = String.format("%010d", maxNumericId + 1);
                    user.setId(nextSequentialId);
                }
            }

            User saved = userRepository.save(user);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
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