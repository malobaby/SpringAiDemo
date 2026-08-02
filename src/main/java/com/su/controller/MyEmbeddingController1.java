package com.su.controller;

import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 测试嵌入模型
 */
@RestController
@RequestMapping("/embedding1")
public class MyEmbeddingController1 {

    @Autowired
    private OpenAiEmbeddingModel embeddingModel;

    @RequestMapping("/test_em1")
    public void testEm1() {
        float[] embedVector1 = embeddingModel.embed("我喜欢吃苹果");

        // 输出2048维数组： [0.016991314, -0.03852576, -0.03954342, -0.008359363, …… ]
        System.out.println(Arrays.toString(embedVector1));
    }

    @RequestMapping("/test_em2")
    public void testEm2() {
        float[] embedVector1 = embeddingModel.embed("我喜欢吃苹果");
        float[] embedVector2 = embeddingModel.embed("我喜欢吃香蕉");
        float[] embedVector3 = embeddingModel.embed("火箭发射升空");

        double v1 = euclideanDistance(embedVector1, embedVector2);
        double v2 = euclideanDistance(embedVector1, embedVector3);

        // [embedVector1和embedVector2的欧式距离] 会比 [embedVector1和embedVector3] 的欧式距离【小】一点儿
        System.out.println("embedVector1和embedVector2的欧式距离 = " + v1);
        System.out.println("embedVector1和embedVector3的欧式距离 = " + v2);
    }



    /**
     * 计算两个向量之间的欧氏距离（Euclidean Distance）。
     *
     * @param a 向量一
     * @param b 向量二
     * @return 欧氏距离
     */
    public static double euclideanDistance(float[] a, float[] b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("向量不能为 null");
        }
        if (a.length != b.length) {
            throw new IllegalArgumentException("两个向量维度必须相同");
        }

        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            double diff = a[i] - b[i];
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }
}
