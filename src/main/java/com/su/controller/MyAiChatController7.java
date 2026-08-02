package com.su.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试会话记忆
 */
@RestController
@RequestMapping("/test7")
public class MyAiChatController7 {

    @Autowired
    private ChatClient chatClientWithMemory;

    /**
     * 会话记忆测试。
     * 注意：由于每个存储是根据id来存的，所以需要传入会话convId
     * @param input 输入
     * @param convId 传话Id，用于区分不同的会话
     */
    @RequestMapping("/ai_memory")
    public String aiMemory(String input, String convId) {
        String response = this.chatClientWithMemory.prompt()
                .user(input)
                .advisors( a -> a.param(ChatMemory.CONVERSATION_ID, convId))
                .call()
                .content();
        return "ok~~~" + response;
    }
}
