package com.qf.shop_search;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopSearchApplicationTests {

	@Autowired
	private SolrClient solrClient;

	/**
	 * 添加索引库
	 */
	@Test
	public void insert() throws IOException, SolrServerException {
		for(int i =0;i< 10;i++) {

			SolrInputDocument document = new SolrInputDocument();
			document.setField("id", i + 1);
			if (i == 5) {
				document.setField("gtitle", "小天鹅洗衣洗衣洗衣洗衣机" + i + 1);
			} else {
				document.setField("gtitle", "小天鹅洗衣机" + i + 1);
			}
			document.setField("ginfo", "这款洗衣机很不错");
			document.setField("gprice", 999.9 + i);
			document.setField("gimage", "http://www.baidu.com");
			document.setField("gcount", 100 + i);

			//将该商品放入索引库
			solrClient.add(document);
		}
			solrClient.commit();
	}

	/**
	 * 修改索引库，其实就是添加索引库，库中没有此id就是添加，有就是进行修改
	 */
	@Test
	public void update(){
		SolrInputDocument document = new SolrInputDocument();
		document.setField("id",1);
		document.setField("gtitle","小天鹅洗衣机(美的造)");
		document.setField("ginfo","这款洗衣机很不错");
		document.setField("gprice",999.9);
		document.setField("gimage","http://www.baidu.com");
		document.setField("gcount",100);
		try {
			//将该商品放入索引库
			solrClient.add(document);
			solrClient.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除
	 */
	@Test
	public void delete() throws IOException, SolrServerException {
		solrClient.deleteById("1");
		//solrClient.deleteByQuery("*:*");
		solrClient.commit();
	}

	/**
	 * 查询
	 */
	@Test
	public void query(){
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("gtitle:洗衣");//设置查询条件
		try {
			QueryResponse query = solrClient.query(solrQuery);//搜索的结果对象
			SolrDocumentList results = query.getResults();//获得搜索的结果集
			for (SolrDocument document : results) {
				String id = (String) document.get("id");
				String gtitle = (String) document.get("gtitle");
				String ginfo = (String) document.get("ginfo");
				float gprice = (float) document.get("gprice");
				int gcount = (int) document.get("gcount");
				String gimage = (String) document.get("gimage");
				System.out.println(id+" "+gtitle+" "+ginfo+" "+gprice+" "+gcount+" "+gimage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

