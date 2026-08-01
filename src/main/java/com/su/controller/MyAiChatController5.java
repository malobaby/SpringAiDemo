package com.su.controller;

import com.su.advisor.MySimpleLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * 测试 PromptTemplate 提示词模板
 */
@RestController
@RequestMapping("/test5")
public class MyAiChatController5 {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ChatClient javaExpertChatClient;

    /**
     * 测试 PromptTemplate
     */
    @RequestMapping(value = "/prompt_template", produces = "text/html;charset=UTF-8")
    public Flux<String> promtTemplate(String input) {
        PromptTemplate promptTemplate = new PromptTemplate("介绍下{topic}");
        Prompt prompt = promptTemplate.create(Map.of("topic", input));
        return chatClient.prompt(prompt)
                .advisors(new MySimpleLoggerAdvisor())  // 添加自定义的 Advisor，方便从控制台中看出真正发给模型的内容
                .stream()
                .content()
                ;
    }

    /**
     * 上面是显式的new一个PromptTemplate对象，也有点儿烦。
     * 使用 `ChatClient` 的流式API可以最快地组装提示词
     */
    @RequestMapping(value = "/prompt_template2", produces = "text/html;charset=UTF-8")
    public Flux<String> promptTemplate2(String input) {
        return chatClient.prompt()
                .system("你是一个专业的书评助手")  // 设置系统提示词
                .user(u -> u.text("请给我三本关于{topic}的书箱").param("topic", input))
                .advisors(new MySimpleLoggerAdvisor())  // 添加自定义的 Advisor，方便从控制台中看出真正发给模型的内容
                .stream()
                .content()
                ;
    }


    /**
     * 测试 “Java 技术专家”的chatClient
     */
    @GetMapping("/java_expert")
    public String java_expert(String input) {
        return javaExpertChatClient.prompt()
                .user(input)
                .call()
                .content();
    }


}
