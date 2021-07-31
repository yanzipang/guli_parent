package com.atguigu.commonutils.response;

import com.atguigu.commonutils.enums.ResultCodeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

// 统一返回结果的工具类
@Data
public class R {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    // 构造方法私有，让别人不用new这个对象，而只能使用此类中的方法
    private R(){}

    public boolean isSuccess() {
        return ResultCodeEnum.SUCCESS.getResultCode().equals(this.code);
    }

    public boolean isNotSuccess() {
        return !isSuccess();
    }

    // 成功静态方法
    public static R ok(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCodeEnum.SUCCESS.getResultCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getResultMessage());
        return r;
    }

    // 失败静态方法
    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCodeEnum.ERROR.getResultCode());
        r.setMessage(ResultCodeEnum.ERROR.getResultMessage());
        return r;
    }

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        // this:谁调用这个方法就代表谁
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
