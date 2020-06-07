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
    private String uavName;
    private List airspaceName;
}
