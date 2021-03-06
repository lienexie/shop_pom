package com.qf.listener;

import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author lien
 * @Date 2019-01-19
 * @Version 1.0
 */

@Component
@RabbitListener(queues = "goods_queue")
public class MyRabbitListener {

    @Autowired
    private Configuration configuration;

    @RabbitHandler
    public void handlerMsg(Goods goods){
        System.out.println("接收到消息："+goods);

        Map<String,Goods> map = new HashMap<>();
        map.put("goods",goods);

        //获得calsspath
        String path = this.getClass().getResource("/static/page/").getPath()+goods.getId()+".html";
        System.out.println("路径"+path);
        try (
                Writer out = new FileWriter(path);//会自动关闭，jdk1.8的写法
        ){
            //准备模板
            Template template = configuration.getTemplate("goods.ftl");
            template.process(map,out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
