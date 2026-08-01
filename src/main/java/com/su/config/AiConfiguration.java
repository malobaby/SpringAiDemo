package com.su.config;

import org.springframework.ai.chat.client.ChatClient;
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

}
