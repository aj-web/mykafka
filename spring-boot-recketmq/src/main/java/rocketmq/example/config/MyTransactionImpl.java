package rocketmq.example.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.StringMessageConverter;

import java.util.concurrent.ConcurrentHashMap;

public class MyTransactionImpl implements RocketMQLocalTransactionListener {

    private ConcurrentHashMap<Object, String> localTrans = new ConcurrentHashMap<>();

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        Object id = msg.getHeaders().get("id");
        String destination = arg.toString();
        localTrans.put(id,destination);
        org.apache.rocketmq.common.message.Message message = RocketMQUtil.convertToRocketMessage(new StringMessageConverter(),"UTF-8",destination, msg);
        String tags = message.getTags();
        if(StringUtils.contains(tags,"TagA")){
            return RocketMQLocalTransactionState.COMMIT;
        }else if(StringUtils.contains(tags,"TagB")){
            return RocketMQLocalTransactionState.ROLLBACK;
        }else{
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        //SpringBoot的消息对象中，并没有transactionId这个属性。跟原生API不一样。
//        String destination = localTrans.get(msg.getTransactionId());
        return RocketMQLocalTransactionState.COMMIT;
    }
}
