package com.ccssoft.cloudtask.vo;

import com.ccssoft.cloudtask.entity.Task;
import lombok.Data;

/**
 * @author moriarty
 * @date 2020/6/5 17:45
 */
@Data
public class TaskVo extends Task {
    private String uavName;
}
