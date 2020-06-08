package com.ccssoft.cloudcommon.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author moriarty
 * @date 2020/5/19 15:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_user")
public class User extends BaseEntity {
    private static final long serialVersionUID = 4010728069921493163L;
    /**
     * 用户名
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

    /**
     * 个人家庭住址/企业地址
     */
    @NotBlank(message = "地址不能为空！")
    private String address;

    /**
     * 身份证号/社会信用代码
     */
    @NotBlank(message = "地址不能为空！")
    private String ip;

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
