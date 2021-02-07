package com.dare.common.exception;

import com.dare.common.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Executor;

/**
 * @Author: shengyao
 * @Package: com.dare.common.exception
 * @Date: 2020/12/3 15:45
 * @Description:
 * @version: 1.0
 */
@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(HttpServletResponse response, Exception e){
//        if (e instanceof DareException){
            return generateErrorMsg(e.getMessage());
//        }
//        return generateErrorMsg("未知错误！");
    }

    private Result generateErrorMsg(String message) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }
}
