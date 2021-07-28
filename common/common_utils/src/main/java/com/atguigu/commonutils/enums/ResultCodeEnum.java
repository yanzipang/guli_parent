package com.atguigu.commonutils.enums;

/**
 * @Author HanGuangKai
 * @Date 2021/7/28 14:41
 * @description
 */
public enum ResultCodeEnum {
    SUCCESS("成功",20000),
    ERROR("失败",20001),
    NO_DATA("无数据",40000);

    private final String resultMessage;
    private final Integer resultCode;

    public String getResultMessage() {
        return resultMessage;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    ResultCodeEnum(String resultMessage, Integer resultCode) {
        this.resultMessage = resultMessage;
        this.resultCode = resultCode;
    }
}
