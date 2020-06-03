package com.ccssoft.cloudcommon.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * @author moriarty
 * @date 2020/5/19 15:32
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -6507594581709611025L;

    @TableId(type = IdType.AUTO)
    public Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    public Date gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")//通过json输出到前端的时候会格式化
    @TableField(fill = FieldFill.INSERT_UPDATE)
    public Date gmtModified;
}
