package com.su.config;

import com.su.advisor.MySimpleLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
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


    /**
     * 创建一个 ChatClient 对象，用于与 OpenAI 的 API 进行交互。
     * 并添加【内置】的 Advisor，用于记录请求和响应信息。
     * 添加系统提示词，用于定制化问答。
     */
    @Bean
    public ChatClient javaExpertChatClient(OpenAiChatModel model) {
        String systemPrompt = """
            你是一个资深的 Java 技术顾问。
            禁止回答任何非技术类问题，例如天气或娱乐八卦。
            代码示例必须符合 Java 17+ 规范。
            回答需要符合以下格式：首先一句话概括问题的核心，然后提供代码示例，最后补充注意事项。
            如果自己不确定，可以说"关于这个问题，我目前没有确切的信息"，禁止编造内容。
            """;
        return ChatClient
                .builder(model)
                .defaultSystem(systemPrompt) // 设置系统提示词
                .defaultAdvisors(new SimpleLoggerAdvisor()) // 设置一个日志Advisor，方便观察请求和响应信息
                .build();
    }

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .maxMessages(10)    // 最多保存10条消息
                .chatMemoryRepository(new InMemoryChatMemoryRepository())   // 使用内存存储
                .build()
                ;
    }

    @Bean
    public ChatClient chatClientWithMemory(OpenAiChatModel model, ChatMemory chatMemory) {
        return ChatClient
                .builder(model)
                .defaultAdvisors(
                        new MySimpleLoggerAdvisor(),  //  设置一个日志Advisor，方便观察请求和响应信息
                        MessageChatMemoryAdvisor.builder(chatMemory).build()    // 设置一个MemoryAdvisor，用于记录请求和响应信息
                )
                .build();
    }


}
