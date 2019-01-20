package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author lien
 * @Date 2019-01-17
 * @Version 1.0
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    @Reference
    private ISearchService searchService;

    /**
     * 根据关键字进行搜索
     * @param keyword
     * @return
     */
    @RequestMapping("/query")
    public String search(String keyword, Model model){

        //调用服务索引库
        List<Goods> goodsList = searchService.queryByIndexed(keyword);
        System.out.println("--->"+goodsList);
        model.addAttribute("goodsList",goodsList);
        return "searchlist";
    }
}
