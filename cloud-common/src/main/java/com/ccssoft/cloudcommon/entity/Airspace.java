package com.ccssoft.cloudcommon.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.ccssoft.cloudcommon.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 * 空域信息
 * </p>
 *
 * @author moriarty
 * @since 2020-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Airspace extends BaseEntity {

    private static final long serialVersionUID = 2716589656476651376L;

    /**
     * 空域名称
     */
    @NotBlank(message = "空域名称不能为空！")
    private String airspaceName;

    /**
     * 空域审批状态 0:禁用 1:正常
     */

    private Integer status;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer deleted;

    /**
     * 具体空域范围坐标
     */
    @NotBlank(message = "空域坐标组不能为空！")
    private String airspacePoint;

    /**
     * 空域开始使用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "空域起止时间不能为空！")
    private Date startTime;

    /**
     * 空域结束使用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "空域起止时间不能为空！")
    private Date endTime;

    /**
     * 附加数据
     */
    @NotNull(message = "空域所属不能为空！")
    private transient Long userId;
}
