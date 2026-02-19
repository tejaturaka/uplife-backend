////package com.example.demo.controller;
////
////import com.example.demo.entity.UserDocument;
////import com.example.demo.repository.DocumentRepository;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.http.HttpHeaders;
////import org.springframework.http.MediaType;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.*;
////import org.springframework.web.multipart.MultipartFile;
////import java.util.List;
////
////@RestController
////@RequestMapping("/api/documents")
////@CrossOrigin(origins = "*")
////public class DocumentController {
////
////    @Autowired
////    private DocumentRepository documentRepository;
////
////    // 1. Upload Document to SQL Database
////    @PostMapping("/upload")
////    public String uploadFile(@RequestParam("file") MultipartFile file, 
////                             @RequestParam("userId") String userId) {
////        try {
////            UserDocument doc = new UserDocument(
////                userId, 
////                file.getOriginalFilename(), 
////                file.getContentType(), 
////                file.getBytes()
////            );
////            documentRepository.save(doc);
////            return "File uploaded successfully: " + file.getOriginalFilename();
////        } catch (Exception e) {
////            return "Upload failed: " + e.getMessage();
////        }
////    }
////
////    // 2. Get List of all Documents for a specific User
////    @GetMapping("/user/{userId}")
////    public List<UserDocument> getUserDocuments(@PathVariable String userId) {
////        return documentRepository.findByUserId(userId);
////    }
////
////    // 3. Download/View Document (Stream from SQL to Browser)
////    @GetMapping("/download/{id}")
////    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
////        UserDocument doc = documentRepository.findById(id)
////                .orElseThrow(() -> new RuntimeException("Document not found"));
////
////        return ResponseEntity.ok()
////                .contentType(MediaType.parseMediaType(doc.getFileType()))
////                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + doc.getFileName() + "\"")
////                .body(doc.getData());
////    }
////}
//
//
//
//package com.example.demo.controller;
//
//import com.example.demo.entity.UserDocument;
//import com.example.demo.repository.DocumentRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/documents")
//@CrossOrigin(origins = "*")
//public class DocumentController {
//
//    @Autowired
//    private DocumentRepository documentRepository;
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, 
//                                             @RequestParam("userId") String userId) {
//        try {
//            if (file.isEmpty()) {
//                return ResponseEntity.badRequest().body("File is empty");
//            }
//
//            UserDocument doc = new UserDocument();
//            doc.setUserId(userId);
//            doc.setFileName(file.getOriginalFilename());
//            doc.setFileType(file.getContentType());
//            doc.setData(file.getBytes());
//            
//            documentRepository.save(doc);
//            return ResponseEntity.ok("Upload successful: " + file.getOriginalFilename());
//        } catch (Exception e) {
//            e.printStackTrace(); // Print error to Koyeb logs
//            return ResponseEntity.internalServerError().body("Server Error: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/user/{userId}")
//    public List<UserDocument> getUserDocuments(@PathVariable String userId) {
//        var docs = documentRepository.findByUserId(userId);
//        // Set data to null to speed up list loading (fetch data only on download)
//        docs.forEach(d -> d.setData(null)); 
//        return docs;
//    }
//
//    @GetMapping("/download/{id}")
//    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
//        UserDocument doc = documentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Document not found"));
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(doc.getFileType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + doc.getFileName() + "\"")
//                .body(doc.getData());
//    }
//}





package com.example.demo.controller;

import com.example.demo.entity.UserDocument;
import com.example.demo.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentRepository documentRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, 
                                             @RequestParam("userId") String userId) {
        try {
            if (file.isEmpty()) return ResponseEntity.badRequest().body("File is empty");

            UserDocument doc = new UserDocument();
            doc.setUserId(userId);
            doc.setFileName(file.getOriginalFilename());
            doc.setFileType(file.getContentType());
            doc.setData(file.getBytes());
            
            documentRepository.save(doc);
            return ResponseEntity.ok("Upload successful");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Server Error: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public List<UserDocument> getUserDocuments(@PathVariable String userId) {
        var docs = documentRepository.findByUserId(userId);
        docs.forEach(d -> d.setData(null)); 
        return docs;
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        UserDocument doc = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + doc.getFileName() + "\"")
                .body(doc.getData());
    }
}