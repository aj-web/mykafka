package consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.omg.CORBA.CTX_RESTRICT_SCOPE;

import java.util.List;

/**
 * @Author ninan
 * @Description
 * @Date 2021/6/25
 * @Description 顺序消费
 */
public class OrderConsumer {

    public static void main(String[] args) {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("please_rename_unique_group_name_3");
        try {
            defaultMQPushConsumer.setNamesrvAddr("192.168.253.101:9876");
            defaultMQPushConsumer.subscribe("TopicTest", "*");
            defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

            defaultMQPushConsumer.registerMessageListener((MessageListenerOrderly) (msgs, context) -> {
                context.setAutoCommit(true);
                for (MessageExt msg : msgs){
                    System.out.println("收到消息内容 "+new String(msg.getBody()));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            });

            defaultMQPushConsumer.start();
            System.out.println("开始顺序消费~");
        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
