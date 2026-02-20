package com.example.demo.controller;

import com.example.demo.entity.Message;
import com.example.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "https://uplife-frontend.vercel.app", allowCredentials = "true") // FORCE CORS HERE
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping("/send")
    public Message send(@RequestBody Message msg) {
        return messageRepository.save(msg);
    }

    @GetMapping("/all")
    public List<Message> getAll() {
        return messageRepository.findAll();
    }
}