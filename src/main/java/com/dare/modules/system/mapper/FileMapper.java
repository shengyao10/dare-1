package com.dare.modules.system.mapper;

import com.dare.modules.system.entity.File;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: shengyao
 * @Package: com.dare.modules.system.mapper
 * @Date: 2021/1/5 15:12
 * @Description:
 * @version: 1.0
 */
@Mapper
@Component(value = "fileMapper")
public interface FileMapper {
    void save(File file1);

    String getFileName(String projectNo, String apqpNo);

    void updateFileState(String projectNo, String apqpNo);

    Integer isUpload(String projectNo, String apqpNo);

    List<File> getFileListBeforeStep(String projectNo, String apqpNo);

    void saveList(@Param("files") List<File> list);

    String getSignFilePath(String projectNo, String apqpNo);

    Integer isExamined(String projectNo, String apqpNo);

    void deleteFile(String projectNo, String apqpNo);
}
