package com.imooc.rabbit.producer.component;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;


        /**
         * 确认消息的回调监听接口，用于确认消息是否被Broker收到
         */
        final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {

            /**
             * @param correlationData producer与broker之间通信的唯一标识
             * @param ack 是否落盘成功
             * @param s 失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String s) {
                System.err.println("消息ACK结果:" + ack + ", correlationData: " + correlationData.getId());
            }
        };

    /**
     * 发送消息的方法
     * @param msg 消息内容本体
     * @param properties 附加的参数信息
     */
    public void send(Object msg, Map<String,Object> properties){

        //1、创建消息，消息=消息本体+消息头
        MessageHeaders headers = new MessageHeaders(properties);
        Message<Object> message = MessageBuilder.createMessage(msg, headers);

        //2、用于监听confirm的内容
        rabbitTemplate.setConfirmCallback(confirmCallback);

        //3、利用rabbitTemplate发送消息，并且配置相关参数

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());  //指定业务唯一的iD

        //回调接受函数，用于接受回调的消息，可以打印出来 todo
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public org.springframework.amqp.core.Message postProcessMessage(org.springframework.amqp.core.Message message) throws AmqpException {
                System.out.println("------------postProcessMessage:" + message);
                return message;
            }
        };

        rabbitTemplate.convertAndSend("exchange-1", "routingKey-1",
                message,
                messagePostProcessor,
                correlationData);
    }
}
