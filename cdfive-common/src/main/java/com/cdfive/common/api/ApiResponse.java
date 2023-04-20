package com.cdfive.common.api;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cdfive
 */
public class ApiResponse<T> implements Serializable {

    // 响应码
    @Getter
    @Setter
    private String code;

    // 消息
    @Getter
    @Setter
    private String msg;

    // 业务数据
    @Getter
    @Setter
    private T data;

    @Getter
    @Setter
    private String traceId;

    public ApiResponse() {
    }

    public ApiResponse(String code) {
        this.code = code;
        this.msg = ResponseConstant.getMsgByCode(code);
    }

    public ApiResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiResponse(String code, T data) {
        this.code = code;
        this.msg = ResponseConstant.getMsgByCode(code);
        this.data = data;
    }

    public ApiResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ApiResponse<String> SUCC = new ApiResponse<String>(ResponseConstant.SUCC);

    public static <T> ApiResponse<T> succ() {
        return new ApiResponse<T>(ResponseConstant.SUCC);
    }

    public static <T> ApiResponse<T> succ(T data) {
        return new ApiResponse<T>(ResponseConstant.SUCC, data);
    }

    public static <T> ApiResponse<T> succMsg(String msg) {
        return new ApiResponse<T>(ResponseConstant.SUCC, msg);
    }

    public static <T> ApiResponse<T> errBizMsg(String msg) {
        return new ApiResponse<T>(ResponseConstant.BUSSINESS_ERR, msg);
    }

    public static <T> ApiResponse<T> errSysMsg() {
        return new ApiResponse<T>(ResponseConstant.INTERNAL_ERR);
    }

    public static <T> ApiResponse<T> errSysMsg(String msg) {
        return new ApiResponse<T>(ResponseConstant.INTERNAL_ERR, msg);
    }

    public static class ResponseConstant {
        public static String SUCC = "0";

        public static String APP_KEY_INVALID = "1";
        public static String PERMISSION_DENY = "2";

        public static String PARAM_EMPTY = "100";
        public static String PARAM_INVALID = "101";

        public static String NOT_LOGIN = "200";
        public static String LOGIN_TIMEOUT = "201";
        public static String OHTER_DEVICE_LOGIN = "202";
        public static String USER_PWD_ERR = "203";
        public static String ACCOUNT_IS_EXIST = "204";
        public static String NOT_REGISTER = "205";

        public static String BUSSINESS_ERR = "300";
        public static String SMS_TIMEOUT = "301";

        public static String INTERNAL_ERR = "500";
        public static String DB_DATA_ERR = "501";
        public static String FREQUENT_OPERATION = "502";

        private static Map<String, String> codeMap = new HashMap<String, String>() {
            {
                put(SUCC, "成功");

                put(APP_KEY_INVALID, "授权非法，禁止访问");
                put(PERMISSION_DENY, "请求非法，禁止访问");

                put(PARAM_EMPTY, "缺少参数");
                put(PARAM_INVALID, "参数非法");

                put(NOT_LOGIN, "请先登录");
                put(LOGIN_TIMEOUT, "登录已超时，请重新登录");
                put(OHTER_DEVICE_LOGIN, "已在其它设备登录");
                put(USER_PWD_ERR, "用户名密码错误");
                put(ACCOUNT_IS_EXIST, "该账号已申请");
                put(NOT_REGISTER, "请先注册");

                put(BUSSINESS_ERR, "业务异常");
                put(SMS_TIMEOUT, "短信验证码超时");

                put(INTERNAL_ERR, "服务器内部错误");
                put(DB_DATA_ERR, "数据错误");
                put(FREQUENT_OPERATION, "频繁操作");
            }
        };

        public static String getMsgByCode(String code) {
            String msg = codeMap.get(code);
            return msg;
        }
    }
}
