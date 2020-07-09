package com.ccssoft.cloudmessagemachine.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.ccssoft.cloudcommon.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.sf.jsqlparser.schema.Database;

/**
 * <p>
 * 记录飞行器的飞行数据
 * </p>
 *
 * @author moriarty
 * @since 2020-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PlanData extends BaseEntity {

    /**
     * 对应飞行器的id
     */
    private Long uavId;

    /**
     * 一组飞行速度
     */
    private String speed;

    /**
     * 一组海拔高度
     */
    private String altitude;

    /**
     * 飞行开始记录时间
     */
    private Date start;

    /**
     * 飞行结束记录时间
     */
    private Date end;

    /**
     * 飞行总距离
     */
    private Double distance;

    /**
     * 一组坐标详情
     */
    private String coordinate;


}
