package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum  ExceptionEnum {
    PRICE_CANNOT_BE_NULL(400, "价格不能为空"),
    USER_LOGIN_ERROR(400,  "账号密码错误"),
    ;
    private int code;
    private String msg;
}
