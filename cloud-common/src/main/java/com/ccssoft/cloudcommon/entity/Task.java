package com.ccssoft.cloudcommon.entity;

import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.ccssoft.cloudcommon.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * <p>
 * 飞行计划
 * </p>
 *
 * @author moriarty
 * @since 2020-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Task extends BaseEntity {
    //@NotNull       相当于  return obj != null  通常用于map或list,对象不能为null,但是可以是空集(size() == 0)
    //@NotEmpty   相当于  return  obj != null and obj.size() > 0  通常用于map或list,对象不能为null并且size() > 0
    //@NotBlank     相当于  return obj != null and obj.trim().length() > 0  用于String类型的校验

    private static final long serialVersionUID = 1238170194062447845L;

    /**
     * 飞行计划名字
     */
    @NotBlank(message = "taskName can't be empty ")
    private String taskName;

    /**
     * 飞机计划说明
     */
    private String description;

    /**
     * 计划飞行开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "planStartTime can't be empty ")
    private Date planStartTime;

    /**
     * 计划飞行结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "planEndTime can't be empty ")
    private Date planEndTime;

    /**
     * 对应计划性质id
     */
    @NotNull(message = "taskNatureId can't be empty ")
    private Integer taskNatureId;

    /**
     * 离地高度
     */
    @NotNull(message = "taskName can't be empty ")
    private Integer height;

    /**
     * 是否在视距内飞行 0：不在 1：在
     */
    @NotNull(message = "withinLineOfSight can't be empty ")
    private Integer withinLineOfSight;

    /**
     * 飞行计划是否批准 0：禁止 1：准飞
     */
    private int status;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer deleted;

    /**
     * 对应执行任务的飞机编号
     */

    @NotNull(message = "uavId can't be empty ")
    private Long uavId;

    /**
     * 对应空域id
     */
    @NotNull(message = "airspaceId can't be empty ")
    private transient List<Long> airspaceId;

}
