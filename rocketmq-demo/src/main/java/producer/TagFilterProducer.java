package producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @Author ninan
 * @Description
 * @Date  2021/6/25
 * @Description 产生Tag标签的数据,供过滤
 * */
public class TagFilterProducer {

    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        try {
            producer.setNamesrvAddr("192.168.253.101:9876");
            producer.start();

            String[] tags = new String[] {"TagA", "TagB", "TagC"};

            for (int i = 0; i < 15; i++) {
                Message msg = new Message("TagFilterTest",
                        tags[i % tags.length],
                        "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));

                SendResult sendResult = producer.send(msg);
                System.out.printf("%s%n", sendResult);
            }

            producer.shutdown();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
