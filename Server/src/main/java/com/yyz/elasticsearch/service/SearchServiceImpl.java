package com.yyz.elasticsearch.service;
import com.yyz.elasticsearch.SearchService;
import com.yyz.elasticsearch.dao.QARepository;
import com.yyz.elasticsearch.entity.QA;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * Created by yaoyongzhen on 18/4/29
 */
@Service("demoService")
public class SearchServiceImpl implements SearchService {
    @Autowired
    private Client client;
    @Autowired
    private QARepository qARepository;
    public String sayHello(String name) {
        System.out.println("received from remote: "+name);
        return "Hello " + name;
    }

    /**
     * 插入一条数据
     * @param qa
     */
    public void addEntity(QA qa) {
        qARepository.save(qa);
    }

    /**
     * 查询
     * @param query
     * @return
     */
    public String search(final String query) {

        System.out.println("query: " + query);

        QueryBuilder queryBuilder = QueryBuilders.matchQuery("q", query);

        SearchResponse response = client.prepareSearch()
                .setQuery(queryBuilder)
                .addHighlightedField("Q")
                .execute().actionGet();

        SearchHit[] searchHitArr = response.getHits().getHits();
        /**
         * 遍历检索到的结果，这里可以自定义排序算法
         */
        for (int i = 0; i < searchHitArr.length; ++i) {
            SearchHit searchHit = searchHitArr[i];
            System.out.print("index:"+searchHit.index()+" type:"+searchHit.getType()+" id: "+searchHit.getId()+" q:"+(String)searchHit.getSource().get("q"));
            List l  = (List)searchHit.getSource().get("a");
            for (int j = 0;j<l.size();j++){
                System.out.print(" a: "+l.get(j));
            }
            System.out.println("");
        }
        /**
         * 返回第一个相关文档
         */
        String result = null;
        if(searchHitArr.length>0){
            SearchHit searchHit = searchHitArr[0];
            result = "index:"+searchHit.index()+" type:"+searchHit.getType()+" id: "+searchHit.getId()+" q:"+(String)searchHit.getSource().get("q");
            List l  = (List)searchHit.getSource().get("a");
            for (int j = 0;j<l.size();j++){
                result = result+ " a: "+l.get(j);
            }
        }
        return result;
    }
}
