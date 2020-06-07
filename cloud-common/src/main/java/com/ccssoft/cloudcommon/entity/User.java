package com.ccssoft.cloudcommon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ccssoft.cloudcommon.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author moriarty
 * @date 2020/5/19 15:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
public class User extends BaseEntity {
    private static final long serialVersionUID = 4010728069921493163L;
    /**
     * 用户id
     */
    @NotBlank(message = "用户名不能为空！")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空！")
    private String password;
    /**
     * 单位名称/姓名
     */
    @NotBlank(message = "名称不能为空！")
    private String name;

    //TODO 需要加个地址？个人怎么加？家庭住址？

    private String salt;

    /**
     * 电子邮箱地址
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 逻辑删除 0:删除 1:没删
     */
    @TableLogic
    private Integer status;

    /**
     * 用户角色
     */
    @NotNull(message = "用户角色为空！")
    private int roleId;

    /*
    可以加个乐观锁，如果有必要的话
    @version
    private int version
     */

}
