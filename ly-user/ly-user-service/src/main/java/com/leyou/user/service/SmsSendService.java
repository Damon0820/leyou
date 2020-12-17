package com.leyou.user.service;

import com.aliyuncs.CommonRequest;
import com.leyou.user.config.SmsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class SmsSendService {

    @Autowired
    SmsConfig smsConfig;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public static String CAPTCHA_PREFI = "user.phone.captcha";

    /**
     * 发送短信
     * @param phone
     * @return
     */
    public Boolean sendSms(String phone) {
//        String preCode = stringRedisTemplate.opsForValue().get(CAPTCHA_PREFI + "phone");
        String code = generateCode(6);
        CommonResponse sendSuccess = this.aliyunSendSms(phone, code);
        if (sendSuccess.getHttpResponse().isSuccess()) {
            // 寸redis
            stringRedisTemplate.opsForValue().set(CAPTCHA_PREFI + phone, code, 5, TimeUnit.MINUTES);
            return true;
        } else {
            return  false;
        }
    }

    /**
     * 发送短信：阿里云短信服务-提供的示例直接修改
     * @param phone
     * @return
     */
    public CommonResponse aliyunSendSms(String phone, String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsConfig.getAccessKeyId(), smsConfig.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", smsConfig.getSignName());
        request.putQueryParameter("TemplateCode", smsConfig.getTemplateCode());
        request.putQueryParameter("TemplateParam", "{\"code\": \"" + code + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            // 手机号，验证码存到redis
            return response;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成指定位数的随机数字
     * @param len 随机数的位数
     * @return 生成的随机数
     */
    public static String generateCode(int len){
        len = Math.min(len, 8);
        int min = Double.valueOf(Math.pow(10, len - 1)).intValue();
        int num = new Random().nextInt(
                Double.valueOf(Math.pow(10, len + 1)).intValue() - 1) + min;
        return String.valueOf(num).substring(0,len);
    }

}
