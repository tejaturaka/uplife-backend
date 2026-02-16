package com.example.demo.repository;

import com.example.demo.entity.UserDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<UserDocument, Long> {
    // Custom method to find all documents uploaded by a specific user
    List<UserDocument> findByUserId(String userId);
}