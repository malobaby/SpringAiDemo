package com.su.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyAiChatController1 {

    @Autowired
    private ChatClient chatClient;

    @GetMapping("/test1_helloworld")
    public String test1_helloworld(String input) {
        return chatClient.prompt()
                .user(input)
                .call()
                .content();
    }
}
