package com.cyshield.Partition.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SenderService {

    private final KafkaTemplate<Integer, String> template;

    public SenderService(KafkaTemplate<Integer, String> template) {
        this.template = template;
    }

    public void send(String toSend, int key) {
        template.send("topic1", key, toSend);
    }
}

