package com.online.edu.eduservice.handler;

import com.online.edu.common.R;
import com.online.edu.common.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    //1、对所有的异常统一处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("出现了异常");
    }

    //2、对特定的异常进行处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("不能除以0");
    }

    //3、自定义的异常进行处理
    @ExceptionHandler(EduException.class)
    @ResponseBody
    public R error(EduException e){
        e.printStackTrace();
        log.error(e.getMessage());//将错误日志输出到文件
        log.error(ExceptionUtil.getMessage(e)); //将日志堆栈信息输出到文件
        return R.error().message(e.getMessage()).code(e.getCode());
    }

}
