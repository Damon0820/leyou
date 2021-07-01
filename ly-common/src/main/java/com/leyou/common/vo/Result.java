package com.leyou.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * http返回体data都数据结构
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    /**
     * 成功调用
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(200, "请求成功", data);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<T>(400, "", null);
    }
}
