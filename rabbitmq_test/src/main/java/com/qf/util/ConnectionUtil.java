package com.qf.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author lien
 * @Date 2019-01-19
 * @Version 1.0
 */
public class ConnectionUtil {

    private static ConnectionFactory factory;

    static {
        factory = new ConnectionFactory();
        factory.setHost("192.168.217.128");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);
        factory.setVirtualHost("/admin");
    }

    public static Connection getConnection(){

        Connection conn = null;
        try {
            conn = factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
