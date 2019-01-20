package com.qf.model2;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author lien
 * @Date 2019-01-19
 * @Version 1.0
 */
public class Provider {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection conn = ConnectionUtil.getConnection();

        Channel channel = conn.createChannel();

        channel.queueDeclare("simple_queue2",false,false,false,null);

        for (int i=0;i<10;i++){

            String str = "hello rabbitmq~~~"+i;

            channel.basicPublish("","simple_queue2",null,str.getBytes("UTF-8"));
        }

        conn.close();
    }
}
