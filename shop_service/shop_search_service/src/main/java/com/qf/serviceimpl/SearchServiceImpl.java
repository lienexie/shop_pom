package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Goods;
import com.qf.entity.Page;
import com.qf.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lien
 * @Date 2019-01-17
 * @Version 1.0
 */
@Service
public class SearchServiceImpl implements ISearchService{

    @Autowired
    private SolrClient solrClient;

    /**
     * 通过关键字查询索引库，返回商品列表
     * @param keyword
     * @return
     */
    @Override
    public List<Goods> queryByIndexed(String keyword) {
        SolrQuery solrQuery = new SolrQuery();
        if(keyword==null){
            solrQuery.setQuery("*:*");
        }else {
            solrQuery.setQuery("gtitle:"+keyword+" || ginfo:"+keyword);
        }

        List<Goods> goodsList = new ArrayList<>();
        try {
            QueryResponse query = solrClient.query(solrQuery);
            SolrDocumentList results = query.getResults();
            for (SolrDocument document : results) {
                String id = (String) document.get("id");
                String gtitle = (String) document.get("gtitle");
                String ginfo = (String) document.get("ginfo");
                float gprice = (float) document.get("gprice");
                int gcount = (int) document.get("gcount");
                String gimage = (String) document.get("gimage");
                Goods goods = new Goods(
                        Integer.parseInt(id),
                        gtitle,
                        ginfo,
                        gcount,
                        0,
                        0,
                        gprice,
                        gimage
                );
                goodsList.add(goods);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return goodsList;
    }



    @Override
    public int insertIndexed(Goods goods) {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id",goods.getId());
        document.setField("gtitle",goods.getTitle());
        document.setField("ginfo",goods.getGinfo());
        document.setField("gprice",goods.getPrice());
        document.setField("gcount",goods.getGcount());
        document.setField("gimage",goods.getGimage());
        try {
            solrClient.add(document);
            solrClient.commit();

            return  1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Page<Goods> getPage(String keyword, String currentPage) {
        Page<Goods> page= new Page<>();
        page.setCurrentPage(1);
        page.setPageSize(3);
        if(currentPage!=null){
            page.setCurrentPage(Integer.parseInt(currentPage));
        }
        SolrQuery solrQuery=new SolrQuery();
        if(keyword == null ){
            solrQuery.setQuery("*:*");
        } else {
            solrQuery.setQuery("gtitle:" + keyword + " || ginfo:" + keyword);
        }

        solrQuery.setStart((page.getCurrentPage()-1)*page.getPageSize());
        solrQuery.setRows(page.getPageSize());
        List<Goods> goodsList=new ArrayList<>();
        try {
            QueryResponse query = solrClient.query(solrQuery);
            SolrDocumentList results = query.getResults();
            page.setTotalCount((int) query.getResults().getNumFound());

            for (SolrDocument document : results) {
                String id = (String) document.get("id");
                String gtitle = (String) document.get("gtitle");
                String ginfo = (String) document.get("ginfo");
                float gprice = (float) document.get("gprice");
                int gcount = (int) document.get("gcount");
                String gimage = (String) document.get("gimage");
                int a=1;
                Goods goods = new Goods(  Integer.parseInt(id),

                        gtitle,
                        ginfo,
                        gcount,
                        0,
                        0,
                        gprice,
                        gimage);
                goodsList.add(goods);


            }
            page.setList(goodsList);
            page.setTotalPage(page.getTotalCount()%page.getPageSize()==0?page.getTotalCount()/page.getPageSize():page.getTotalCount()/page.getPageSize()+1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }
}
