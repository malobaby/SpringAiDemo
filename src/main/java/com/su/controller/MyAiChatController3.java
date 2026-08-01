package com.su.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 测试 openai 的【流式】对话
 */
@RestController
@RequestMapping("/test3")
public class MyAiChatController3 {

    @Autowired
    private ChatClient chatClient;

    /**
     * 测试 openai 的【流式】对话
     * 这么写会有乱码
     */
    @RequestMapping("/hi_stream")
    public Flux<String> hiStream(String input) {
        return chatClient.prompt()
                .user(input)
                .stream()
                .content()
                ;
    }

    /**
     * 测试 openai 的【流式】对话
     * 处理【乱码】问题
     */
    @RequestMapping(value = "/hi_stream_normal", produces = "text/html;charset=UTF-8")
    public Flux<String> hiStreamNormal(String input) {
        return chatClient.prompt()
                .user(input)
                .stream()
                .content()
                ;
    }


}
