package com.su.controller;

import com.su.advisor.MySimpleLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试结构化输出
 */
@RestController
@RequestMapping("/test6")
public class MyAiChatController6 {

    @Autowired
    private ChatClient chatClient;

    // 使用 Java 16+ 的 record 语法，编译器会自动生成 equals()、hashCode()、toString() 方法
    public record TopicBooks(
            String topic,
            List<String> books
    ) {}


    /**
     * 结构化输出测试
     */
    @GetMapping(value = "/ai_format_output")
    String generationFormatOutput(String input) {
        TopicBooks entity = this.chatClient.prompt()
                .system("你是一个专业的书评助手")// 创建Prompt对象，用于构建聊天请求
                .user(u -> u.text("请给我三本关于{topic}的书籍").param("topic", input)) // 设置用户输入
                .advisors(new MySimpleLoggerAdvisor())  // 为了方便看清发送给模型的内容，添加一个自定义的日志Advisor
                .call()
                .entity(TopicBooks.class);
        System.out.println("entity = " + entity);
        return "ok~~~" + entity.toString();
    }


    // 使用 Java 16+ 的 record 语法，编译器会自动生成 equals()、hashCode()、toString() 方法
    public record BookReview(
            String name,    // 书名
            String author,  // 作者
            String reviewer, // 书评人
            int rating,     // 评分（1~5）
            String comment,  // 评价
            String  money // 摘要
    ) {}

    /**
     * 结构化输出测试（测试集合）
     */
    @GetMapping(value = "/ai_format_output2")
    String generationFormatOutput2(String input) {
        List<BookReview> bookReviewList = this.chatClient.prompt()
                .user(u -> u.text("请给我关于 {topic} 书籍的3条评价信息").param("topic", input)) // 设置用户输入
                .advisors(new MySimpleLoggerAdvisor())  // 为了方便看清发送给模型的内容，添加一个自定义的日志Advisor
                .call()
                .entity(new ParameterizedTypeReference<List<BookReview>>() {
                })
                ;
        System.out.println("entity = " + bookReviewList);
        return "ok~~~" + bookReviewList;
    }

}
