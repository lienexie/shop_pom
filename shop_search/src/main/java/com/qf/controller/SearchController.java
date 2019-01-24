package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.entity.Page;
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

    @RequestMapping("/queryPage")
    public String pageBysearch(String keyword,String currentPage, Model model){

        Page<Goods> page = searchService.getPage(keyword, currentPage);
        System.out.println("当前页:"+currentPage);
        System.out.println("关键字:"+keyword);
        System.out.println("总条数:"+page.getTotalCount());
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "searchlist";
    }
}
