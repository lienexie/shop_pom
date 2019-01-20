package com.qf.controller;

import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author lien
 * @Date 2019-01-18
 * @Version 1.0
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private Configuration configuration;

    @RequestMapping("/createhtml")
    @ResponseBody
    public String creaeHtml(@RequestBody Goods goods) {

        //将json-->Goods
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
        return "ok";
    }

    /*public void test() throws Exception {
        //准备模板
        Template template = configuration.getTemplate("hello.ftl");

        //准备数据
        Map map = new HashMap();
        map.put("name","wonderful!!!");

        //组合生成静态页
        Writer out = new FileWriter("C:\\Users\\lien\\Desktop\\hello.html");
        template.process(map,out);

        out.close();
    }*/
}
