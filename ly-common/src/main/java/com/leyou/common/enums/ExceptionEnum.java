package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum  ExceptionEnum {
    // 服务内部错误
    SYSTEM_ERROR(500, "系统错误"),

    // 鉴权
    TOKEN_ERROR(999, "token错误，过期或者无效"),

    // 账户相关错误
    SMS_VALIDATE_NO_EXIST(400, "没有有效的验证码，需要重新发送验证码"),
    SMS_VALIDATE_FAIL(400, "验证码不正确"),
    REGISTER_COUNT_EXIST(400, "账号已经被注册"),
    REGISTER_PHONE_EXIST(400, "手机号已经被注册"),

    // 商品类业务错误
    PRICE_CANNOT_BE_NULL(400, "价格不能为空"),
    USER_LOGIN_ERROR(400,  "账号密码错误"),
    ;
    private int code;
    private String msg;
}
