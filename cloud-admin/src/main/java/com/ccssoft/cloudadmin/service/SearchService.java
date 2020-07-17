package com.ccssoft.cloudadmin.service;

import java.util.List;
import java.util.Map;

/**
 * @author moriarty
 * @date 2020/7/10 17:40
 */
public interface SearchService {
    List<Map<String,Object>> searchPage(String keyword, int pageNo, int pageSize);
}
