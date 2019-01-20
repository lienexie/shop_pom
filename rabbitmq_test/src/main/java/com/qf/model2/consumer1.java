package com.qf.model2;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author lien
 * @Date 2019-01-19
 * @Version 1.0
 */
public class consumer1 {
    public static void main(String[] args) throws IOException {
        Connection conn = ConnectionUtil.getConnection();

        Channel channel = conn.createChannel();

        channel.queueDeclare("simple_queue2",false,false,false,null);

        channel.basicConsume("simple_queue2",true,new DefaultConsumer(channel){

            //消息消费的方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者接收到消息："+new String(body,"UTF-8"));
            }
        });
    }
}
