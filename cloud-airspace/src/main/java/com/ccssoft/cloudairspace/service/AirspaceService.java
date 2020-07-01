package com.ccssoft.cloudairspace.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccssoft.cloudcommon.entity.Airspace;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 空域信息 服务类
 * </p>
 *
 * @author moriarty
 * @since 2020-06-01
 */
public interface AirspaceService extends IService<Airspace> {

    /**
     * 注册空域，并且注册与其相关联的用户关系
     * @param airspace 空域详情包含与之相关的用户id
     * @return 成功与否
     */
    int registerAirSpace(Airspace airspace);

    /**
     * 批准空域
     * @param airspaceId 空域id
     * @return 成功与否
     */
    int approvalById(Long airspaceId);

    /**
     * 通过用户id获取与之相匹配的一组空域
     * @param userId
     * @return 成功获取则返回一组空域详情，如果失败则返回null
     */
    List<Airspace> getAirspaceByUserId(Long userId);

    /**
     * 通过空域id获取与之对应的空域信息
     * @param airspaceId 空域id
     * @return 空域详情
     */
    Airspace getAirspaceByAirspaceId(Long airspaceId);

    /**
     * 通过用户id与计划预计开始时间获取符合条件的对应空域信息
     * @param userId 用户id
     * @param date 计划预计开始时间
     * @return 成功获取则返回一组空域详情，如果失败则返回null
     */
    List<Airspace> getAirspaceByUserIdPremiseTime(Long userId, String date);

    /**
     * 获取未批准的空域分页数据
     * @param current 当前页数
     * @param size 每页数据量
     * @return 分页数据
     */
    Page<Airspace> getAllAirspaceNotAllow(int current, int size);

    /**
     * 获取一组对应所需一组空域id的空域详情
     * @param AirspaceIdlist 一组空域id
     * @return 一组空域详情
     */
    List<Airspace> getAirspaceByAirspaceIds(List<Long> AirspaceIdlist);

    /**
     * 修改空域id，并在每次修改之后需要把批准状态置换为未批准
     * @param airspace 空域详情
     * @return 成功与否
     */
    int updateAirSpace(Airspace airspace);

    /**
     * 获取未批准的空域数量
     * @return 数量
     */
    Integer getNoApprovaledCount();

    /**
     * 获取已批准的空域数量
     * @return 数量
     */
    Integer getApprovaledCount();

    /**
     * 以分页的形式获取与userid相关的空域详情
     * @param current 当前页数
     * @param size 每页的数量
     * @param userId 用户id
     * @return R
     */
    Page<Airspace> getAirspaceByUserId4Page(int current, int size, Long userId);
}
