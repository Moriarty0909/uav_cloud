package com.ccssoft.cloudadmin.service.impl;


import com.ccssoft.cloudadmin.service.SearchService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author moriarty
 * @date 2020/7/10 17:46
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Resource
    private RestHighLevelClient client;

    @Override
    public List<Map<String, Object>> searchPage(String keyword, int pageNo, int pageSize) {
        if (pageNo <= 1) {
            pageNo = 1;
        }
        //条件搜索
        SearchRequest searchRequest = new SearchRequest("jd_goods");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //分页
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);

        //精准匹配
        MatchQueryBuilder termQueryBuilder = QueryBuilders.matchQuery("title", keyword);
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //构建高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.requireFieldMatch(false);//关闭多个高亮显示！
        highlightBuilder.preTags("<span style='color:red'>");//前缀
        highlightBuilder.postTags("</span>");//后缀
        sourceBuilder.highlighter(highlightBuilder);

        //执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse search = null;
        try {
            search = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Map<String,Object>> list = new ArrayList<>();
        //解析结果
        for (SearchHit documentFields : search.getHits().getHits()) {

            Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();//获取高亮的信息，现在里面就包含了title
            HighlightField title = highlightFields.get("title");//又有两个元素
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();//获取原来的数据没需要把高亮字段替换掉之前的
            if (title != null) {
                Text[] fragments = title.fragments();
                String n_title = "";
                for (Text fragment : fragments) {
                    n_title += fragment;
                }
                sourceAsMap.put("title",n_title);//替换
            }

            list.add(sourceAsMap);
        }

        return list;
    }
}
