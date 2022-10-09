package com.ray.common.exception;

import lombok.Getter;
import org.springframework.util.StringUtils;

/**
 * @author chengrui
 * @date 2022/10/9 11:31:24
 */
public class CommonException extends RuntimeException{
    @Getter
    protected String errMsg;            // 错误提示信息，显示给用户
    @Getter
    protected String detailMsg;         // 错误的具体信息，可能包含一些参数ID等信息
    @Getter
    protected CommonErrorCode error;    // 错误码
    @Getter
    protected Object data;              // 返回内容

    public CommonException(CommonErrorCode error) {
        super(error.getDesc());
        this.error = error;
        this.errMsg = error.getDesc();
        this.detailMsg = error.getDesc();
    }

    public CommonException setData(Object data) {
        this.data = data;
        return this;
    }

    public CommonException(CommonErrorCode error, String errMsg) {
        super(errMsg);
        this.error = error;
        this.errMsg = errMsg;
        this.detailMsg = errMsg;
    }

    public CommonException(CommonErrorCode error, Throwable cause) {
        super(error.getDesc(), cause);
        this.error = error;
        this.errMsg = error.getDesc();
        this.detailMsg = error.getDesc();
    }

    public CommonException(CommonErrorCode error, String errMsg, String detailMsg) {
        super(StringUtils.isEmpty(detailMsg) ? errMsg : detailMsg);
        this.error = error;
        this.errMsg = errMsg;
        this.detailMsg = detailMsg;
    }

    public CommonException(CommonErrorCode error, String errMsg, Throwable cause) {
        super(errMsg, cause);
        this.error = error;
        this.errMsg = errMsg;
        this.detailMsg = errMsg;
    }

    public CommonException(CommonErrorCode error, String errMsg,
                           String detailMsg, Throwable cause) {
        super(StringUtils.isEmpty(detailMsg) ? errMsg : detailMsg, cause);
        this.error = error;
        this.errMsg = errMsg;
        this.detailMsg = detailMsg;
    }
}