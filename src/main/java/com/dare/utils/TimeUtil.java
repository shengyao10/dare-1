package com.dare.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: shengyao
 * @Package: com.dare.utils
 * @Date: 2020/10/29 9:40
 * @Description: 时间工具类
 * @version: 1.0
 */
@Component
public class TimeUtil {

    /**
     * @Author: shengyao
     * @Description: 获取当前时间  yyyy-MM-dd HH:mm:ss
     * @Date: 2020/10/29 9:55
     * @param
     * @Return String  20201029095542
     * @return
     */
    public static Date getCurrentTime() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.parse(dateFormat.format(date));
    }

    /**
     * @Author: shengyao
     * @Description: 获取当前时间 yyyyMMddHHmmss
     * @Date: 2020/10/29 9:53
     * @param
     * @Return String  20201029095326
     */
    public static String getCurrentTimeToNo(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * @Author: shengyao
     * @Description: 获取当前年份
     * @Date: 2020/11/20 11:33
     * @param
     * @Return java.lang.String
     */
    public static String getCurrentYear(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static void main(String[] args) {
        System.out.println(TimeUtil.getCurrentTimeToNo());
    }

}
