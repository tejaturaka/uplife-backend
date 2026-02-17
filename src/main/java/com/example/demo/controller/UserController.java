package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "https://uplife-frontend.vercel.app") // Backup annotation
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerOrUpdate(@RequestBody User user) {
        try {
            // REQUIREMENT: Generate 10-char Unique ID only for NEW users
            // Format: State(2) + Dist(2) + Mandal(2) + Seq(4)
            if (user.getId() == null || user.getId().trim().isEmpty()) {
                
                // 1. Get Abbreviations
                String stateCode = getAbbreviation(user.getState());
                String distCode = getAbbreviation(user.getDist());
                String mandalCode = getAbbreviation(user.getMandal());
                
                // Example Prefix: TEHYAM
                String idPrefix = (stateCode + distCode + mandalCode).toUpperCase();
                
                // 2. Find Next Sequence
                List<User> allUsers = userRepository.findAll();
                int maxSequence = 0;

                for (User u : allUsers) {
                    if (u.getId() != null && u.getId().length() == 10 && u.getId().startsWith(idPrefix)) {
                        try {
                            String seqStr = u.getId().substring(6); // Last 4 digits
                            int seq = Integer.parseInt(seqStr);
                            if (seq > maxSequence) maxSequence = seq;
                        } catch (Exception e) { /* Ignore non-numeric */ }
                    }
                }

                // 3. Generate ID (Prefix + 0001)
                String nextId = idPrefix + String.format("%04d", maxSequence + 1);
                user.setId(nextId);
            }

            User saved = userRepository.save(user);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    private String getAbbreviation(String input) {
        if (input == null || input.trim().isEmpty()) return "XX";
        input = input.trim().replaceAll("\\s+", "").toUpperCase();
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