package producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @Author ninan
 * @Description
 * @Date  2021/6/25
 * @Description 延时发送消息
 * */
public class ScheduledMessageProducer {
    public static void main(String[] args) {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("ExampleProducerGroup");

        try {
            defaultMQProducer.setNamesrvAddr("192.168.253.101:9876");
            defaultMQProducer.start();

            int totalMessagesToSend = 100;
            for (int i = 0; i < totalMessagesToSend; i++) {
                Message message = new Message("TestTopic", ("Hello scheduled message " + i).getBytes());
                message.setDelayTimeLevel(3);
                defaultMQProducer.send(message);
            }

            defaultMQProducer.shutdown();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
