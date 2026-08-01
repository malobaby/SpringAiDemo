package com.su.config;

import com.su.advisor.MySimpleLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfiguration {

    /**
     * 创建一个 ChatClient 对象，用于与 OpenAI 的 API 进行交互。
     * 注：OpenAiChatModel是由框架提供的，来源于{@link org.springframework.ai.model.openai.autoconfigure.OpenAiChatAutoConfiguration}
     */
    @Bean
    public ChatClient chatClient(OpenAiChatModel model) {
        return ChatClient
                .builder(model)
                .build();
    }

    /**
     * 创建一个 ChatClient 对象，用于与 Ollama 的 API 进行交互。
     * 注：OllamaChatModel是由框架提供的，来源于{@link org.springframework.ai.model.ollama.autoconfigure.OllamaChatAutoConfiguration}
     */
    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel model) {
        return ChatClient
                .builder(model)
                .build();
    }

    /**
     * 创建一个 ChatClient 对象，用于与 OpenAI 的 API 进行交互。
     * 并添加【自定义】的 Advisor，用于记录请求和响应信息。
     */
    @Bean
    public ChatClient customAdvisorChatClient(OpenAiChatModel model) {
        return ChatClient
                .builder(model)
                .defaultAdvisors(new MySimpleLoggerAdvisor()) // 添加自定义的 Advisor
                .build();
    }

    /**
     * 创建一个 ChatClient 对象，用于与 OpenAI 的 API 进行交互。
     * 并添加【内置】的 Advisor，用于记录请求和响应信息。
     */
    @Bean
    public ChatClient inbuildAdvisorChatClient(OpenAiChatModel model) {
        return ChatClient
                .builder(model)
                .defaultAdvisors(new SimpleLoggerAdvisor()) // 添加内置的 Advisor
                .build();
    }

}
