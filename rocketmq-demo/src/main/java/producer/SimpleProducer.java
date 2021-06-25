package producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @Author ninan
 * @Description
 * @Date 2021/6/24
 * @Description 同步发送消息
 **/
public class SimpleProducer {
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("MyTestCluster");

        defaultMQProducer.setNamesrvAddr("192.168.253.101:9876");
        defaultMQProducer.start();

        for (int i = 0; i < 10; i++) {
            Message message = new Message("TopicTest", "TagA", "OrderID00001", "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));

            defaultMQProducer.sendOneway(message);
        }
        Thread.sleep(5000);
        defaultMQProducer.shutdown();

    }
}
