package com.su.advisor;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import reactor.core.publisher.Flux;

/**
 * 实现 CallAdvisor和StreamAdvisor 接口，同时支持两种模式
 */
public class MySimpleLoggerAdvisor implements CallAdvisor, StreamAdvisor {
    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        System.out.println("发送请求前，request = " + chatClientRequest);
        ChatClientResponse response = callAdvisorChain.nextCall(chatClientRequest);
        System.out.println("接收到响应，response = " + response);
        return response;
    }

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest, StreamAdvisorChain streamAdvisorChain) {
        System.out.println("发送流式请求前，request = " + chatClientRequest);
        return streamAdvisorChain.nextStream(chatClientRequest)
                .doOnNext(response -> System.out.println("接收到流式响应片段，response = " + response));
    }

    @Override
    public String getName() {
        return "su的简单日志Advisor";
    }

    /**
     * 这个getOrder()方法用于指定Advisor（通知器）的执行顺序。
     * 作用说明：
     * 返回值越小，优先级越高，越早执行
     * 返回 0 表示高优先级
     * 如果有多个Advisor，Spring AI 会按照此值从小到大依次执行
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
