package com.dare.utils;

import java.text.DecimalFormat;

/**
 * @Author: shengyao
 * @Package: com.dare.utils
 * @Date: 2020/11/20 11:07
 * @Description: 编号生成类
 * @version: 1.0
 */
public class NumberGenerateUtil {

    /**
     * @param count
     * @Author: shengyao
     * @Description: 项目编号生成
     * @Date: 2020/11/20 19:34
     * @Return java.lang.String
     */
    public static String ProjectNoGenerate(Integer count) {
        StringBuffer sb = new StringBuffer();
        DecimalFormat dft = new DecimalFormat("000");
        String enterpriseCode = "FD04";
        String xm = "XM";
        String no = dft.format(count);
        String year = TimeUtil.getCurrentYear();
        String symbol = "-";

        sb.append(enterpriseCode).append(symbol).append(xm).append(no).append(symbol).append(year);

        return sb.toString();
    }

    public static String APQPFileNoGenerate(String projectNo, String step) {
        String symbol = "-";
        StringBuffer sb = new StringBuffer();
        String[] str1 = projectNo.split("-");
        for (int i = 0; i < (str1.length-1); i++) {
            sb.append(str1[i]).append(symbol);
        }
//        sb.deleteCharAt(sb.length()-1);
//        System.out.println(sb);

        StringBuffer sb2 = new StringBuffer();
        String[] str2 = step.split("\\.");
        for (String a : str2
        ) {
            sb2.append(a).append(".");
        }
        if (str2.length != 3) {
            sb2.append("1");
        } else {
            sb2.deleteCharAt(sb2.length() - 1);
        }
        String year = TimeUtil.getCurrentYear();

        sb.append("FDB10041").append(symbol).append(sb2).append(symbol).append(year);
//        System.out.println(sb.toString());

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(ProjectNoGenerate(100));
        System.out.println(APQPFileNoGenerate("FD04-XM001-2020","1.15"));
    }

}
