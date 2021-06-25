package stream.rocketmq.basic;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping("/MQTest")
public class MQTestController {

    @Resource
    private ScProducer producer;


    @RequestMapping("/sendMessage")
    public String sendMessage(String message){
        producer.sendMessage(message);
        return "消息发送完成";
    }
}
