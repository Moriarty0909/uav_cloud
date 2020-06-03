package com.ccssoft.cloudadmin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author moriarty
 * @date 2020/5/20 15:48
 */
@Repository
public interface RoleDao {
    String getRoleNameById(int id);
}
