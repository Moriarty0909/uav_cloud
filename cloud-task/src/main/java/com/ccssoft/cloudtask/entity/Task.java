package com.ccssoft.cloudtask.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class Task implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "task_id", type = IdType.AUTO)
    private Long taskId;

    /**
     * 飞行计划名字
     */
    private String taskName;

    /**
     * 飞机计划说明
     */
    private String description;

    /**
     * 计划飞行开始时间
     */
    private Date planStartTime;

    /**
     * 计划飞行结束时间
     */
    private Date planEndTime;

    /**
     * 对应空域id
     */
    private Long airspaceId;

    /**
     * 对应计划性质id
     */
    private Integer taskNatureId;

    /**
     * 离地高度
     */
    private Integer height;

    /**
     * 是否在视距内飞行 0：不在 1：在
     */
    private Integer withinLineOfSight;

    /**
     * 对应执行任务的飞机编号
     */
    private Long uavId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
