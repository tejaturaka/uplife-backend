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
@CrossOrigin(origins = "https://uplife-frontend.vercel.app", allowCredentials = "true") // FORCE CORS HERE
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