package rocketmq.example.basic;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author ninan
 * @Description
 * @Date  2021/6/25
 * @Description 消费者
 * */
@Component
@RocketMQMessageListener(consumerGroup = "MyConsumerGroup" , topic = "TestTopic",consumeMode= ConsumeMode.CONCURRENTLY)
public class SpringConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.println(message);
    }
}
