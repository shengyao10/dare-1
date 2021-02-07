package com.dare.common.exception;

import com.dare.common.vo.Result;

/**
 * @Author: shengyao
 * @Package: com.lbj.exception
 * @Date: 2020/11/9 15:14
 * @Description:
 * @version: 1.0
 */
public class DareException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public  DareException(){

    }
    public  DareException(String message){
        super(message);
    }

    public DareException(Throwable cause)
    {
        super(cause);
    }

    public DareException(String message, Throwable cause)
    {
        super(message,cause);
    }


}
