package com.ccssoft.cloudairspace.datainit;

import com.ccssoft.cloudairspace.dao.AirspaceDao;
import com.ccssoft.cloudcommon.entity.Airspace;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author moriarty
 * @date 2020/7/10 17:52
 */
@Component
public class ElasticSearchDataInit {
    @Resource
    private AirspaceDao airspaceDao;

    @Resource
    private RestHighLevelClient client;

    /**
     * 加载航线数据至es，方便后续使用。
     */
    @PostConstruct
    public void init () throws IOException {
        List<Airspace> airspaces = airspaceDao.selectList(null);

        GetIndexRequest request = new GetIndexRequest("airspace");
        if ( client.indices().exists(request, RequestOptions.DEFAULT)) {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("airspace");
            client.indices().delete(deleteIndexRequest,RequestOptions.DEFAULT);
        }

        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        for (Airspace airspace : airspaces) {
            bulkRequest.add(new IndexRequest().id(String.valueOf(airspace.getId())).source(airspace));
        }
        client.bulk(bulkRequest,RequestOptions.DEFAULT);
    }
}
