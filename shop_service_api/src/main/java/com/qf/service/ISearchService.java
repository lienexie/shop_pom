package com.qf.service;

import com.qf.entity.Goods;
import com.qf.entity.Page;

import java.util.List;

public interface ISearchService {

    List<Goods> queryByIndexed(String keyword);

    int insertIndexed(Goods goods);

    Page<Goods> getPage(String keyword,String currentPage);
}
