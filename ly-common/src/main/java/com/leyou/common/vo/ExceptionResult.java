package com.leyou.common.vo;

import com.leyou.common.enums.ExceptionEnum;
import lombok.Data;

@Data
public class ExceptionResult {
    private int code;
    private String message;

    public ExceptionResult(ExceptionEnum em) {
        this.code = em.getCode();
        this.message = em.getMsg();
    }
}
