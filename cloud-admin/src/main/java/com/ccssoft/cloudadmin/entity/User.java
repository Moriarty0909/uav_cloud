package com.ccssoft.cloudadmin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ccssoft.cloudcommon.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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

    private String username;

    private String password;

    private String name;

    private String salt;

    private String email;

    private String mobile;

    @TableLogic//逻辑删除
    private Integer status;

    private int roleId;

    /*
    可以加个乐观锁，如果有必要的话
    @version
    private int version
     */

}
