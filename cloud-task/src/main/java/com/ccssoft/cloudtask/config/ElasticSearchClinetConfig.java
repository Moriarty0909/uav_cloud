package com.ccssoft.cloudtask.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author moriarty
 * @date 2020/7/6 12:51
 */
@Configuration
public class ElasticSearchClinetConfig {
    @Bean
    public RestHighLevelClient restHighLevelClient () {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("134.192.243.35",9200,"http"))
        );
        return client;
    }
}
