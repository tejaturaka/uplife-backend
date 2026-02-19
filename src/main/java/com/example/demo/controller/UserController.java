////////
////////package com.example.demo.controller;
////////
////////import com.example.demo.entity.User;
////////import com.example.demo.repository.UserRepository;
////////import org.springframework.beans.factory.annotation.Autowired;
////////import org.springframework.http.ResponseEntity;
////////import org.springframework.web.bind.annotation.*;
////////import java.util.*;
////////
////////@RestController
////////@RequestMapping("/api/users")
////////@CrossOrigin(origins = "*") // Allows access from any frontend
////////public class UserController {
////////
////////    @Autowired
////////    private UserRepository userRepository;
////////
////////    @PostMapping("/register")
////////    public ResponseEntity<?> registerOrUpdate(@RequestBody User user) {
////////        try {
////////            // FIX: Always trim whitespace from Name, Email, and Password to prevent login issues
////////            if (user.getPassword() != null) user.setPassword(user.getPassword().trim());
////////            if (user.getEmail() != null) user.setEmail(user.getEmail().trim());
////////
////////            // LOGIC: Generate ID only if it's a NEW registration
////////            if (user.getId() == null || user.getId().trim().isEmpty()) {
////////                
////////                String role = user.getRole(); 
////////                List<User> allUsers = userRepository.findAll();
////////
////////                // -----------------------------------------------------
////////                // AGENT -> Location ID (e.g., TEHYAM0001)
////////                // -----------------------------------------------------
////////                if ("agent".equalsIgnoreCase(role)) {
////////                    String stateCode = getAbbreviation(user.getState());
////////                    String distCode = getAbbreviation(user.getDist());
////////                    String mandalCode = getAbbreviation(user.getMandal());
////////                    
////////                    String idPrefix = (stateCode + distCode + mandalCode).toUpperCase();
////////                    int maxSequence = 0;
////////
////////                    for (User u : allUsers) {
////////                        if (u.getId() != null && u.getId().startsWith(idPrefix)) {
////////                            try {
////////                                String seqStr = u.getId().substring(6); 
////////                                int seq = Integer.parseInt(seqStr);
////////                                if (seq > maxSequence) maxSequence = seq;
////////                            } catch (Exception e) {}
////////                        }
////////                    }
////////                    user.setId(idPrefix + String.format("%04d", maxSequence + 1));
////////                } 
////////                
////////                // -----------------------------------------------------
////////                // CUSTOMER -> Numeric ID (e.g., 0000000001)
////////                // -----------------------------------------------------
////////                else {
////////                    long maxNumericId = 0;
////////                    for (User u : allUsers) {
////////                        if (u.getId() != null) {
////////                            try {
////////                                long currentId = Long.parseLong(u.getId());
////////                                if (currentId > maxNumericId) maxNumericId = currentId;
////////                            } catch (NumberFormatException e) { /* Ignore non-numeric IDs */ }
////////                        }
////////                    }
////////                    user.setId(String.format("%010d", maxNumericId + 1));
////////                }
////////            } else {
////////                // If updating existing user, ensure ID is trimmed
////////                user.setId(user.getId().trim());
////////            }
////////
////////            User saved = userRepository.save(user);
////////            return ResponseEntity.ok(saved);
////////        } catch (Exception e) {
////////            e.printStackTrace();
////////            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
////////        }
////////    }
////////
////////    // Helper for Abbreviations
////////    private String getAbbreviation(String input) {
////////        if (input == null || input.trim().isEmpty()) return "XX";
////////        input = input.trim().toUpperCase();
////////        String[] parts = input.split(" ");
////////        if (parts.length > 1) return "" + parts[0].charAt(0) + parts[1].charAt(0);
////////        if (input.length() >= 2) return input.substring(0, 2);
////////        return input + "X";
////////    }
////////
////////    @PostMapping("/login")
////////    public ResponseEntity<?> login(@RequestBody User creds) {
////////        // FIX: Handle Nulls and Trim Whitespace
////////        if (creds.getId() == null || creds.getPassword() == null) {
////////            return ResponseEntity.status(400).body("Missing ID or Password");
////////        }
////////
////////        String cleanId = creds.getId().trim();
////////        String cleanPass = creds.getPassword().trim();
////////
////////        System.out.println("Login Attempt: " + cleanId); // Debug Log
////////
////////        Optional<User> userOpt = userRepository.findById(cleanId);
////////
////////        if (userOpt.isPresent()) {
////////            User u = userOpt.get();
////////            // Compare passwords (trimmed)
////////            if (u.getPassword().equals(cleanPass)) {
////////                return ResponseEntity.ok(u);
////////            } else {
////////                System.out.println("Login Failed: Wrong Password");
////////                return ResponseEntity.status(401).body("Invalid Password");
////////            }
////////        } else {
////////            System.out.println("Login Failed: User Not Found");
////////            return ResponseEntity.status(404).body("User ID Not Found");
////////        }
////////    }
////////
////////    @GetMapping("/all")
////////    public List<User> getAll() { return userRepository.findAll(); }
////////
////////    @DeleteMapping("/{id}")
////////    public ResponseEntity<?> delete(@PathVariable String id) {
////////        userRepository.deleteById(id);
////////        return ResponseEntity.ok().build();
////////    }
////////}
//////
//////
//////
//////
//////package com.example.demo.controller;
//////
//////import com.example.demo.entity.User;
//////import com.example.demo.repository.UserRepository;
//////import org.springframework.beans.factory.annotation.Autowired;
//////import org.springframework.http.ResponseEntity;
//////import org.springframework.web.bind.annotation.*;
//////
//////import java.util.List;
//////import java.util.Optional;
//////
//////@RestController
//////@RequestMapping("/api/users")
//////public class UserController {
//////
//////    @Autowired
//////    private UserRepository userRepository;
//////
//////    @PostMapping("/login")
//////    public ResponseEntity<?> login(@RequestBody User loginRequest) {
//////        Optional<User> user = userRepository.findById(loginRequest.getId());
//////        if (user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword())) {
//////            return ResponseEntity.ok(user.get());
//////        }
//////        return ResponseEntity.status(401).body("Invalid ID or Password");
//////    }
//////
//////    @PostMapping("/register")
//////    public User register(@RequestBody User user) {
//////        if (user.getId() == null || user.getId().isEmpty()) {
//////            long count = userRepository.count() + 1;
//////            String prefix = user.getRole().equals("agent") ? "DADADA" : "000000";
//////            user.setId(String.format("%s%04d", prefix, count));
//////        }
//////        return userRepository.save(user);
//////    }
//////
//////    @GetMapping("/all")
//////    public List<User> getAll() {
//////        return userRepository.findAll();
//////    }
//////
//////    @DeleteMapping("/{id}")
//////    public void deleteUser(@PathVariable String id) {
//////        userRepository.deleteById(id);
//////    }
//////}
////
////
////
////
////package com.example.demo.controller;
////
////import com.example.demo.entity.User;
////import com.example.demo.repository.UserRepository;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.*;
////
////import java.util.List;
////import java.util.Optional;
////
////@RestController
////@RequestMapping("/api/users")
////@CrossOrigin(originPatterns = "*", allowCredentials = "true") // Explicitly allow all
////public class UserController {
////
////    @Autowired
////    private UserRepository userRepository;
////
////    @PostMapping("/login")
////    public ResponseEntity<?> login(@RequestBody User loginRequest) {
////        Optional<User> user = userRepository.findById(loginRequest.getId());
////        if (user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword())) {
////            return ResponseEntity.ok(user.get());
////        }
////        return ResponseEntity.status(401).body("Invalid ID or Password");
////    }
////
////    @PostMapping("/register")
////    public User register(@RequestBody User user) {
////        if (user.getId() == null || user.getId().isEmpty()) {
////            long count = userRepository.count() + 1;
////            String prefix = user.getRole().equals("agent") ? "DADADA" : "000000";
////            user.setId(String.format("%s%04d", prefix, count));
////        }
////        return userRepository.save(user);
////    }
////
////    @GetMapping("/all")
////    public List<User> getAll() {
////        return userRepository.findAll();
////    }
////
////    @DeleteMapping("/{id}")
////    public void deleteUser(@PathVariable String id) {
////        userRepository.deleteById(id);
////    }
////}
//
//package com.example.demo.controller;
//
//import com.example.demo.entity.User;
//import com.example.demo.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/users")
//public class UserController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody User loginRequest) {
//        Optional<User> user = userRepository.findById(loginRequest.getId());
//        if (user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword())) {
//            return ResponseEntity.ok(user.get());
//        }
//        return ResponseEntity.status(401).body("Invalid ID or Password");
//    }
//
//    @PostMapping("/register")
//    public User register(@RequestBody User user) {
//        if (user.getId() == null || user.getId().isEmpty()) {
//            long count = userRepository.count() + 1;
//            String prefix = user.getRole().equals("agent") ? "DADADA" : "000000";
//            user.setId(String.format("%s%04d", prefix, count));
//        }
//        return userRepository.save(user);
//    }
//
//    @GetMapping("/all")
//    public List<User> getAll() {
//        return userRepository.findAll();
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable String id) {
//        userRepository.deleteById(id);
//    }
//}

package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        Optional<User> user = userRepository.findById(loginRequest.getId());
        if (user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(401).body("Invalid ID or Password");
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        if (user.getId() == null || user.getId().isEmpty()) {
            long count = userRepository.count() + 1;
            String prefix = user.getRole().equals("agent") ? "DADADA" : "000000";
            user.setId(String.format("%s%04d", prefix, count));
        }
        return userRepository.save(user);
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
    }
}