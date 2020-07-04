package com.ccssoft.cloudauth.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONSupport;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.ccssoft.cloudauth.service.CodeService;
import com.ccssoft.cloudauth.utils.RedisUtil;
import com.ccssoft.cloudcommon.common.utils.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author moriarty
 * @date 2020/7/4 22:11
 */
@Service
public class CodeServiceImpl implements CodeService {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean sendCode(String phone) {
        String code = String.valueOf(redisUtil.get(phone));
        if (!StringUtils.isEmpty(code)) {
            return false;
        }

        //填写好对应的access可以
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GK2nyVtkZh5VkshB1qy", "");
        IAcsClient client = new DefaultAcsClient(profile);
        //这几项不需要动
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("AddSmsSign");
        //自定义参数，手机号验证码签名
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName","无人机云管理平台");
        request.putQueryParameter("TemplateCode","SMS_195261074");

        //构建一个短信验证码
        HashMap<String, Object> map = new HashMap<>();
        code = String.valueOf(RandomUtil.randomInt(1000,9999));
        map.put("code", code);
        request.putQueryParameter("TempateParam", JSONObject.toJSONString(map));

        try {
            CommonResponse response = client.getCommonResponse(request);
            if (response.getHttpResponse().isSuccess()) {
                redisUtil.set(phone,code,300);
            }
        } catch (ServerException e) {
            e.printStackTrace();
            return false;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean verifyCode(String code, String phone) {
        String oneCode = String.valueOf(redisUtil.get(phone));
        if (code.equals(oneCode)) {
            return true;
        } else {
            return false;
        }

    }
}
