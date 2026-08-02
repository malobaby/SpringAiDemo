package com.su.controller;

import com.su.tool.DateTimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试ai调用工具
 */
@RestController
@RequestMapping("/test8")
public class MyAiChatController8 {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private DateTimeTools dateTimeTools;

    /**
     * 带tool的
     */
    @GetMapping("/ai_tool")
    String aiTool(String input) {
        String content = this.chatClient.prompt()
                .user(input)
                .advisors(new SimpleLoggerAdvisor()) // 为了方便看清发送给模型内容，添加一个日志Advisor
                .tools(dateTimeTools) // 注册工具
                .call()
                .content();
        return content;
    }
}
