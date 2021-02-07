package com.dare.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;

/**
 * @Author: shengyao
 * @Package: com.dare.utils
 * @Date: 2020/11/9 17:36
 * @Description:
 * @version: 1.0
 */
public class JsonResourceUtil {

    private JsonResourceUtil() {

    }

    //filename 为文件名字 如 “/json/app_version_info.json”
    public static JSONObject getJsonObjFromResource(String filename) {
        JSONObject json = null;

        if (!filename.contains(".json")) {
            filename += ".json";
        }

        try {

            URL url = JsonResourceUtil.class.getResource(filename);
            String path = url.getPath();
            File file = new File(path);
            if (file.exists()) {
                String content = FileUtils.readFileToString(file, "UTF-8");
                json = JSON.parseObject(content);
            } else {
//                logger.info("file not exist!");
            }

        } catch (Exception e) {
            e.printStackTrace();
//            logger.info("readFileToString" + e.getMessage());
        }
        return json;
    }

    public static String readJsonFile(String fileName){
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
