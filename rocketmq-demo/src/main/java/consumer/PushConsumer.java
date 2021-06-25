package consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @Author ninan
 * @Description
 * @Date
 * @Description push模式消费
 **/
public class PushConsumer {

    public static void main(String[] args) {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("MyTestPushConsumer");
        try {
            defaultMQPushConsumer.setNamesrvAddr("192.168.253.101:9876");
            defaultMQPushConsumer.subscribe("TopicTest","*");
            defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            //defaultMQPushConsumer.setConsumeTimestamp("20181109221800");
            defaultMQPushConsumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
                System.out.println("push模式收到消息"+Thread.currentThread().getName()+msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            defaultMQPushConsumer.start();
            System.out.println("MessageListenerConcurrently启动了");
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
