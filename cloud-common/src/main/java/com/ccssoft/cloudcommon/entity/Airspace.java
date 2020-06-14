package com.ccssoft.cloudcommon.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.ccssoft.cloudcommon.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("空域名称，不能为空。")
    @NotBlank(message = "空域名称不能为空！")
    private String airspaceName;

    /**
     * 空域审批状态 0:禁用 1:正常
     */
    @ApiModelProperty("空域状态，无需处理，默认禁用，后续会由管理进行是否批准用")
    private Integer status;

    /**
     * 逻辑删除
     */
    @TableLogic
    @ApiModelProperty("逻辑删除，无需处理。")
    private Integer deleted;

    /**
     * 具体空域范围坐标
     */
    @NotBlank(message = "空域坐标组不能为空！")
    @ApiModelProperty("空域坐标点，不能为空，格式为以LineString（开头。")
    private String airspacePoint;

    /**
     * 空域开始使用时间
     */
    @ApiModelProperty("空域开始使用时间，不能为空。")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "空域起止时间不能为空！")
    private Date startTime;

    /**
     * 空域结束使用时间
     */
    @ApiModelProperty("空域结束使用时间，不能为空。")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "空域起止时间不能为空！")
    private Date endTime;

    /**
     * 附加数据
     */
    @ApiModelProperty("空域的所属，即userid，用于填入关系列表，不能为空。")
    @NotNull(message = "空域所属不能为空！")
    private transient Long userId;
}
