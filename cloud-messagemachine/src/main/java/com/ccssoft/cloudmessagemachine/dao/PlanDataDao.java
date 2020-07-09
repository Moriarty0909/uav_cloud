package com.ccssoft.cloudmessagemachine.dao;

import com.ccssoft.cloudmessagemachine.entity.PlanData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 记录飞行器的飞行数据 Mapper 接口
 * </p>
 *
 * @author moriarty
 * @since 2020-07-09
 */

public interface PlanDataDao extends BaseMapper<PlanData> {

    int insertData(PlanData data);
}
