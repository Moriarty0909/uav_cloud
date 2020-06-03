package com.ccssoft.cloudtask.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class TaskNatrue implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 飞机计划性质id
     */
    @TableId(value = "task_natrue_id", type = IdType.AUTO)
    private Integer taskNatrueId;

    /**
     * 飞行计划性质名称
     */
    private String taskNatrueName;

    /**
     * 具体描述
     */
    private String description;


}
