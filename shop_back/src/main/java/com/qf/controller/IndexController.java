package com.qf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author lien
 * @Date 2019-01-16
 * @Version 1.0
 */
@Controller
public class IndexController {

    @RequestMapping("/topage/{page}")
    public String toPage(@PathVariable("page") String page){
        return page;
    }
}
