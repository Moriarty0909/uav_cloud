package com.ccssoft.cloudcommon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.ccssoft.cloudcommon.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 无人机设备信息
 * </p>
 *
 * @author moriarty
 * @since 2020-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Uav extends BaseEntity {
    private static final long serialVersionUID = -8473721265610879292L;
    /**
     * 无人机昵称
     */
    @ApiModelProperty("无人机昵称,方便各单位查看管理自己的无人机.")
    private String nickname;
    /**
     * 无人机厂商
     */
    @ApiModelProperty("无人机厂商名称,不能为空.")
    @NotBlank(message = "无人机厂商名称不能为空！")
    @TableField("Manufacturer_name")
    private String manufacturerName;

    /**
     * 无人机型号
     */
    @ApiModelProperty("无人机型号,不能为空.")
    @NotBlank(message = "无人机型号名称不能为空！")
    private String uavType;

    /**
     * 空重
     */
    @ApiModelProperty("无人机空重,不能为空.")
    @NotNull(message = "重量不能为空！")
    private Double weight;

    /**
     * 最大飞行速度
     */
    @ApiModelProperty("无人机最大飞行速度,不能为空.")
    @NotNull(message = "最大时速不能为空！")
    private Double speedMax;


    /**
     * 逻辑删除 0:删除 1:没删
     */
    @ApiModelProperty("逻辑删除,无需处理.")
    @TableLogic
    private Integer deleted;

    /**
     * 附加数据
     */
    @ApiModelProperty("无人机的拥有者,不能为空.")
    @NotNull(message = "所有者不能为空！")
    private transient Long userId;


}
