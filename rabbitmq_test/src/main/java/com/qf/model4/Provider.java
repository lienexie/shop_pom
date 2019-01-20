package com.qf.model4;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @Author lien
 * @Date 2019-01-19
 * @Version 1.0
 */
public class Provider {
    public static void main(String[] args) throws IOException{
        Connection conn = ConnectionUtil.getConnection();

        Channel channel = conn.createChannel();

        //声明交换机
        channel.exchangeDeclare("simple_exchange","direct");

        //声明两个队列
        channel.queueDeclare("queue1",false,false,false,null);

        channel.queueDeclare("queue2",false,false,false,null);

        //队列绑定交换机
        //最后一个参数路由键随机改
        channel.queueBind("queue1","simple_exchange","add");
        channel.queueBind("queue1","simple_exchange","update");

        channel.queueBind("queue2","simple_exchange","add");
        channel.queueBind("queue2","simple_exchange","query");
        channel.queueBind("queue2","simple_exchange","delete");
        //发送消息
        String str = "hello world";
        channel.basicPublish("simple_exchange","add",null,str.getBytes("UTF-8"));

        //关闭连接
        conn.close();
    }
}
