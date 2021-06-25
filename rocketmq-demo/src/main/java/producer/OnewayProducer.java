package producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @Author ninan
 * @Description
 * @Date 2021/6/25
 * @Description单向发送消息
 **/
public class OnewayProducer {
    public static void main(String[] args) {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("MyTestCluster");

        try {
            defaultMQProducer.setNamesrvAddr("192.168.253.101:9876");
            defaultMQProducer.start();
            for (int i = 0; i < 10; i++) {
                Message msg = new Message("TopicTest",
                        "TagA",
                        "OrderID188",
                        "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                defaultMQProducer.sendOneway(msg);
            }

            defaultMQProducer.shutdown();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
