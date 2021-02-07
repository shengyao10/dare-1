package com.dare.modules.system.service;

import com.dare.modules.system.entity.File;
import io.swagger.models.auth.In;

import java.text.ParseException;
import java.util.List;

/**
 * @Author: shengyao
 * @Package: com.dare.modules.system.service
 * @Date: 2020/12/10 18:48
 * @Description: 文件业务类
 * @version: 1.0
 */
public interface FileService {

    String getFileName(String projectNo, String apqpNo);

    void save(File file1, String projectNo, String apqpNo, Integer isUpload) throws ParseException;

    Integer isUpload(String projectNo, String apqpNo);

    List<File> getFileListBeforeStep(String projectNo, String apqpNo);

    void saveList(List<File> list);

    String getSignFilePath(String projectNo, String apqpNo);

    Integer isExamined(String projectNo, String apqpNo);
}
