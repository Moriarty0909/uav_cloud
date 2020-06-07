package com.ccssoft.cloudtask.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 飞行计划与空域关系列表
 * </p>
 *
 * @author moriarty
 * @since 2020-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TaskAirspace implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 计划id
     */
    private Long taskId;

    /**
     * 空域id
     */
    private Long airspaceId;

    public TaskAirspace(Long taskId, Long airspaceId) {
        this.taskId = taskId;
        this.airspaceId = airspaceId;
    }
}
