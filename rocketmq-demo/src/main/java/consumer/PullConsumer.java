package consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author ninan
 * @Description
 * @Date 2021/6/25
 * @Description 使用拉模式拉取消息
 **/
public class PullConsumer {

    private static final Map<MessageQueue, Long> OFFSE_TABLE = new HashMap<>();

    public static void main(String[] args) {
        DefaultMQPullConsumer defaultMQPullConsumer = new DefaultMQPullConsumer("MyTestPullConsumer");
        try {
            defaultMQPullConsumer.setNamesrvAddr("192.168.253.101:9876");
            defaultMQPullConsumer.start();
            Set<MessageQueue> messageSet = defaultMQPullConsumer.fetchSubscribeMessageQueues("TopicTest");
            for (MessageQueue messageQueue : messageSet) {
                if (messageQueue.getQueueId() !=0){
                    continue;
                }
                System.out.println("消费了队列" + messageQueue);
                SINGLE_MQ:
                while (true) {
                    PullResult pullResult = defaultMQPullConsumer.pullBlockIfNotFound(messageQueue, null, getMessageQueueOffset(messageQueue), 32);
                    System.out.println("拉取结果pullResult" + pullResult);
                    putMessageQueueOffset(messageQueue, pullResult.getNextBeginOffset());
                    switch (pullResult.getPullStatus()) {
                        case FOUND:
                            break;
                        case NO_NEW_MSG:
                            break SINGLE_MQ;
                        case NO_MATCHED_MSG:
                            break;
                        case OFFSET_ILLEGAL:
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = OFFSE_TABLE.get(mq);
        if (offset != null)
            return offset;

        return 0;
    }

    private static void putMessageQueueOffset(MessageQueue mq, long offset) {
        OFFSE_TABLE.put(mq, offset);
    }
}
