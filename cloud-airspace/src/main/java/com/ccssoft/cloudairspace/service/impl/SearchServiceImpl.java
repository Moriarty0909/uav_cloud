package com.ccssoft.cloudairspace.service.impl;


import cn.hutool.core.lang.Console;
import com.ccssoft.cloudairspace.service.SearchService;
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
        SearchRequest searchRequest = new SearchRequest("airspace");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //分页
        sourceBuilder.from((pageNo - 1) * pageSize);
        sourceBuilder.size(pageSize);

        //精准匹配,会先进行分词处理，在开始检索。
        MatchQueryBuilder termQueryBuilder = QueryBuilders.matchQuery("airspaceName", keyword);
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //构建高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("airspaceName");
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
            //获取高亮的信息，现在里面就包含了airspace_name
            Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();
            HighlightField title = highlightFields.get("title");//又有两个元素
            //获取原来的数据没需要把高亮字段替换掉之前的
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            if (title != null) {
                Text[] fragments = title.fragments();
                String n_title = "";
                for (Text fragment : fragments) {
                    n_title += fragment;
                }
                //替换
                sourceAsMap.put("airspaceName",n_title);
            }

            list.add(sourceAsMap);
        }
        Console.log(list);
        return list;
    }
}
