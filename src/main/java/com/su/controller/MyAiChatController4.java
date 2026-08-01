package com.su.controller;

import com.su.advisor.MySimpleLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 测试自定义的 Advisor
 */
@RestController
@RequestMapping("/test4")
public class MyAiChatController4 {

    @Autowired
    private ChatClient customAdvisorChatClient;

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ChatClient inbuildAdvisorChatClient;

    /**
     * 测试【自定义】的 Advisor 的 call() 方法
     * 注：customAdvisorChatClient 上配置了Advisor，所以会打印日志
     */
    @RequestMapping("/hi_custom_advisor_call")
    public String hiCustomAdvisor(String input) {
        return customAdvisorChatClient.prompt()
                .user(input)
                .call()
                .content();
    }

    /**
     * 测试【自定义】的 Advisor 的 stream() 方法
     * 注：customAdvisorChatClient 上配置了Advisor，所以会打印日志
     */
    @RequestMapping(value = "/hi_custom_advisor_stream", produces = "text/html;charset=UTF-8")
    public Flux<String> hiCustomAdvisorStream(String input) {
        return customAdvisorChatClient.prompt()
                .user(input)
                .stream()
                .content()
                ;
    }

    /**
     * 测试【自定义】的 Advisor 的 call() 方法
     * 注：chatClient 上没有配置Advisor，所以不会打印日志
     */
    @GetMapping("/test_no_advirsor_on_client")
    public String test_no_advirsor_on_client(String input) {
        return chatClient.prompt()
                .user(input)
                .call()
                .content();
    }

    /**
     * 在 call() 方法上测试【自定义】的 Advisor
     * 注：chatClient 上没有配置Advisor，但是在下面发起调用的时候添加了【自定义】的Advisor，所以会打印日志
     */
    @GetMapping("/test_no_advirsor_on_client2")
    public String test_no_advirsor_on_client2(String input) {
        return chatClient.prompt()
                .advisors(new MySimpleLoggerAdvisor())
                .user(input)
                .call()
                .content();
    }

    /**
     * 在 call() 方法上测试【内置】的 Advisor
     * 注：inbuildAdvisorChatClient 上配置了【内置】的Advisor，所以会打印日志
     */
    @GetMapping("/test_inbuild_advirsor_on_client")
    public String test_inbuild_advirsor_on_client(String input) {
        return inbuildAdvisorChatClient.prompt()
                .user(input)
                .call()
                .content();
    }
}
