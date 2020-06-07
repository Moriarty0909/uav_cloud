package com.ccssoft.cloudcommon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.ccssoft.cloudcommon.entity.BaseEntity;
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

    private String nickname;
    /**
     * 无人机厂商
     */
    @NotBlank(message = "无人机厂商名称不能为空！")
    @TableField("Manufacturer_name")
    private String manufacturerName;

    /**
     * 无人机型号
     */
    @NotBlank(message = "无人机厂商名称不能为空！")
    private String uavType;

    /**
     * 空重
     */
    @NotNull(message = "重量不能为空！")
    private Double weight;

    /**
     * 最大飞行速度
     */
    @NotNull(message = "最大时速不能为空！")
    private Double speedMax;


    /**
     * 逻辑删除 0:删除 1:没删
     */
    @TableLogic
    private Integer deleted;

    /**
     * 附加数据
     */
    @NotNull(message = "所有者不能为空！")
    private transient Long userId;


}
