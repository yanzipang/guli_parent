package com.atguigu.servicebase.exceptionhandler;




import com.atguigu.commonutils.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * 统一异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 由于项目表单需要校验字段过多，一个一个去判空太麻烦，所以用了@NotNull注解，字段为空会抛出MethodArgumentNotValidException异常。
     * 接下来要取得@NotNull(message=“自定义异常”)里的message内容给前端显示
     * 用法：定义一个全局异常处理，一旦发生该类异常，就会捕捉处理，返回给前端信息，返回类Result要根据自己项目业务需要来定义
     * springBoot后台验证接收的参数是否不合法时，会抛出一个BlndException异常，获取message的自定义信息并返回
     * e.getBindingResult().getFieldError().getDefaultMessage()就是获取 @NotNull、@NotBlank等注解中的message中的异常信息
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)   // 异常处理器
    @ResponseBody
    public R error(MethodArgumentNotValidException e){
        log.error(e.getMessage()); // 将错误信息写到文件中
        e.printStackTrace();
        // return R.error().message(e.getBindingResult().getFieldError().getDefaultMessage());
        String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return R.error().message(message);
    }

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