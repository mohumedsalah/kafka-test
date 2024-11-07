package com.cyshield.Partition.controller;


import com.cyshield.Partition.services.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApi {

    @Autowired
    SenderService senderService;

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message, @RequestParam int key) {
        senderService.send(message, key);
        return "Message sent successfully";
    }
}
