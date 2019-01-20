package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author lien
 * @Date 2019-01-16
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable{

    @TableId(type = IdType.AUTO)//主键回填
    private int id;
    private String title;
    private String ginfo;
    private int gcount;
    private int tid;//分类表的外键
    private double allprice;
    private double price;
    private String gimage;
}
