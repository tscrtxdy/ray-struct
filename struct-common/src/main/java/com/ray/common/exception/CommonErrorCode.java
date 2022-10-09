package com.ray.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chengrui
 * @date 2022/10/9 11:32:40
 */
@AllArgsConstructor
public enum CommonErrorCode {
    SUCCESS(200, "执行成功"),

    USER_NOT_LOGIN(400, "用户未登录"),
    VALIDATE_ERROR(401, "校验数据错误"),
    NOT_EXIST(404, "不存在"),
    FORBIDDEN(403, "权限不足"),
    RESOURCE_NOT_FOUND(404, "资源未找到");

    /**
     * 错误代码
     */
    @Getter
    private Integer code;
    /**
     * 错误信息描述
     */
    @Getter
    private String desc;
}
