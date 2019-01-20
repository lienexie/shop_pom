package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author lien
 * @Date 2019-01-16
 * @Version 1.0
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private IGoodsService goodsService;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Value("${fdfs.serverpath}")
    private String fdfsPath;

    @RequestMapping("/list")
    public String goodsManager(Model model){
        List<Goods> goods = goodsService.queryAll();
        System.out.println(goods);
        model.addAttribute("goods",goods);
        model.addAttribute("fdfsPath",fdfsPath);
        return "goodslist";
    }

    @RequestMapping("/uploadimg")
    @ResponseBody
    public String uploadImg(MultipartFile file) throws Exception {
        System.out.println("上传的文件名称"+file.getOriginalFilename());
        //将该图片上传到fastDFS服务上
        StorePath result = fastFileStorageClient.uploadImageAndCrtThumbImage(
                file.getInputStream(),
                file.getSize(),
                "png",
                null);
        System.out.println("上传到dfs上的图片的路径"+result.getFullPath());
        //json数据
        return "{\"imgpath\":\""+result.getFullPath()+"\"}";
    }

    @RequestMapping("/insert")
    public String insertGoods(Goods goods){
        goodsService.insert(goods);
        return "redirect:/goods/list";
    }
}
