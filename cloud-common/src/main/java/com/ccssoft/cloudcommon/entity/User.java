package com.ccssoft.cloudcommon.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("用户名，不能为空。")
    @NotBlank(message = "用户名不能为空！")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty("密码，不能为空。")
    @NotBlank(message = "密码不能为空！")
    private String password;
    /**
     * 单位名称/姓名
     */
    @ApiModelProperty("单位名称/姓名，不能为空。")
    @NotBlank(message = "名称不能为空！")
    private String name;

    /**
     * 个人家庭住址/企业地址
     */
    @ApiModelProperty("个人家庭住址/企业地址，不能为空。")
    @NotBlank(message = "地址不能为空！")
    private String address;

    /**
     * 身份证号/社会信用代码
     */
    @ApiModelProperty("身份证号/社会信用代码，不能为空。")
    @NotBlank(message = "证件号不能为空！")
    private String ip;

    private String salt;

    /**
     * 电子邮箱地址
     */
    @ApiModelProperty("电子邮箱地址")
    private String email;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String mobile;

    /**
     * 逻辑删除 0:删除 1:没删
     */
    @TableLogic
    @ApiModelProperty("逻辑删除，无需处理")
    private Integer status;

    /**
     * 用户角色
     */
    @ApiModelProperty("对应的权限角色id，不能为空。")
    @NotNull(message = "用户角色为空！")
    private int roleId;

    /*
    可以加个乐观锁，如果有必要的话
    @version
    private int version
     */

}
