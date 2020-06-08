package com.ccssoft.cloudtask.vo;

import com.ccssoft.cloudcommon.entity.Task;
import lombok.Data;

import java.util.List;

/**
 * @author moriarty
 * @date 2020/6/5 17:45
 */
@Data
public class TaskVo extends Task {
    /**
     * 无人机名称
     */
    private String uavName;

    /**
     * 空域名称
     */
    private List airspaceName;

    /**
     * 用途名称
     */
    private String natrueName;
}
