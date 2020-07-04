package com.ccssoft.cloudauth.service;

import com.ccssoft.cloudcommon.common.utils.R;

/**
 * @author moriarty
 * @date 2020/7/4 22:05
 */
public interface CodeService {

    /**
     * 发送手机验证码给用户进行注册实名制
     * @param phone 手机号码
     * @return 是否发送成功
     */
    boolean sendCode(String phone);

    /**
     * 验证对应手机的验证码是否正确
     * @param code
     * @return
     */
    boolean verifyCode(String code,String phone);
}
