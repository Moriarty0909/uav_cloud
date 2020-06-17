package com.ccssoft.clouduav.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccssoft.cloudcommon.entity.Uav;

/**
 * <p>
 * 无人机设备信息 服务类
 * </p>
 *
 * @author moriarty
 * @since 2020-05-28
 */
public interface UavService extends IService<Uav> {

    int saveUav(Uav uav, Long userId);

    Page getUav4Page(int current, int size);

    Page getUavByUserId4Page(int current, int size, Long id);

    int getUavCount();
}
