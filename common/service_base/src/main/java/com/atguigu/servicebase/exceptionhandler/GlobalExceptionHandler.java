package com.atguigu.servicebase.exceptionhandler;




import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 全局异常处理
    // 指定出现什么异常会执行此方法
    @ExceptionHandler(Exception.class)   // 异常处理器
    @ResponseBody
    public R error(Exception e){
        log.error(e.getMessage()); // 将错误信息写到文件中
        e.printStackTrace();
        return R.error().message("执行了全局异常处理！");
    }

    // 特定异常处理 ArithmeticException
    @ExceptionHandler(ArithmeticException.class)   // 异常处理器
    @ResponseBody
    public R errorArithmeticException(ArithmeticException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error().message("执行了特定异常处理——ArithmeticException异常！");
    }

    // 自定义异常处理
    @ExceptionHandler(GuliException.class)   // 异常处理器
    @ResponseBody
    public R errorGuliException(GuliException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }


}