package producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Author ninan
 * @Description
 * @Date 2021/6/25
 * @Description 顺序发送消息
 **/
public class OrderProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("MyTestOrderProducer");

        defaultMQProducer.setNamesrvAddr("192.168.253.101:9876");
        defaultMQProducer.start();

        for (int i = 0; i < 10; i++) {

            int orderId = 1;

            for (int j = 0; j < 5; j++) {
                Message msg = new Message("OrderTopicTest", "order_" + orderId, "KEY" + orderId,
                        ("order_" + orderId + " step " + j).getBytes(RemotingHelper.DEFAULT_CHARSET));

                SendResult sendResult = defaultMQProducer.send(msg, (mqs, msg1, arg) -> {
                    Integer id = (Integer) arg;
                    int index = id % mqs.size();
                    return mqs.get(index);
                }, orderId);

                System.out.println(sendResult);
            }

        }

        defaultMQProducer.shutdown();
        Thread.sleep(5000);
        defaultMQProducer.shutdown();

    }
}
