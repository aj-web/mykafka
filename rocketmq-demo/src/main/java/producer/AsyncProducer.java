package producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

/**
 * @Author ninan
 * @Description
 * @Date 2021/6/25
 * @Description 异步发送消息
 **/
public class AsyncProducer {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("DefaultCluster");
        try {
            defaultMQProducer.start();
            defaultMQProducer.setRetryTimesWhenSendAsyncFailed(0);
            for (int i = 0; i < 10; i++) {
                Message message = new Message("TopicTest", "TagA", "OrderID188", "This is Order message".getBytes(StandardCharsets.UTF_8));
                defaultMQProducer.send(message, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        countDownLatch.countDown();
                        System.out.println("消息发送成功啦");
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        countDownLatch.countDown();
                        System.out.println("消息发送失败!");
                    }
                });
            }

            countDownLatch.await();
            defaultMQProducer.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
