package com.ccssoft.cloudairspace.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.ccssoft.cloudcommon.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;

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
    private String airspaceName;

    /**
     * 空域审批状态 0:禁用 1:正常
     */
    @TableLogic
    private Integer status;

    /**
     * 具体空域范围坐标
     */
    private String airspacePoint;

    /**
     * 空域开始使用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 空域结束使用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     * 附加数据
     */
    private transient Object data;
}
