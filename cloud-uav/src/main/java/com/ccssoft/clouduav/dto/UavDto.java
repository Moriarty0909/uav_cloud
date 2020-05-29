package com.ccssoft.clouduav.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ccssoft.clouduav.entity.Uav;
import lombok.Data;

/**
 * @author moriarty
 * @date 2020/5/29 14:32
 */
@Data
public class UavDto {
    private static final long serialVersionUID = 5340604300041840968L;

    private Long userId;

    private String nickname;

    private String manufacturerName;

    private String uavType;

    private Double weight;

    private Double speedMax;

    public Uav getUav () {
        Uav uav = new Uav();
        uav.setNickname(nickname);
        uav.setManufacturerName(manufacturerName);
        uav.setUavType(uavType);
        uav.setWeight(weight);
        uav.setSpeedMax(speedMax);
        return uav;
    }
}
