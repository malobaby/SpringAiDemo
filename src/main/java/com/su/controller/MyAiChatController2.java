package com.su.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test2")
public class MyAiChatController2 {

    @Autowired
    private ChatClient ollamaChatClient;

    @GetMapping("/hi_ollama")
    public String hiOllama(String input) {
        return ollamaChatClient.prompt()
                .user(input)
                .call()
                .content();
    }
}
