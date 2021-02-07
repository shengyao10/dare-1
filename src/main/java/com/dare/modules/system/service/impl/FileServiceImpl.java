package com.dare.modules.system.service.impl;

import com.dare.modules.sign.entity.Sign;
import com.dare.modules.sign.entity.SignPerson;
import com.dare.modules.sign.mapper.SignMapper;
import com.dare.modules.system.entity.File;
import com.dare.modules.system.mapper.FileMapper;
import com.dare.modules.system.service.FileService;
import com.dare.utils.PdfSignUtil;
import com.dare.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: shengyao
 * @Package: com.dare.modules.system.service.impl
 * @Date: 2020/12/10 18:49
 * @Description: 文件业务实现类
 * @version: 1.0
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private SignMapper signMapper;

    @Override
    public void save(File file, String projectNo, String apqpNo, Integer isUpload) throws ParseException {
        if (isUpload==1){
            signMapper.deleteSignPerson(projectNo, apqpNo);
            fileMapper.deleteFile(projectNo, apqpNo);
        }
        file.setGmtCreated(TimeUtil.getCurrentTime());
        StringBuffer sb = new StringBuffer();
        sb.append("/mnt/minio/data/");
        sb.append(projectNo.toLowerCase());
        sb.append("/");
        sb.append(apqpNo);
        sb.append("/会签表.pdf");
        file.setSignFile(sb.toString());
        //另存会签模板并写明编制时间
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String time = formatter.format(date);
        Map<Object, Object> treeDto = new HashMap<>();
        treeDto.put("time1", time);
        String newPDFPath = "/mnt/minio/data/" + projectNo.toLowerCase() + "/" + apqpNo + "/会签表.pdf";
        PdfSignUtil.fillPdf("/mnt/minio/data/template/countersign/会签模板.pdf", newPDFPath, treeDto);
        SignPerson signPerson = new SignPerson();
        Sign sign = new Sign();
        signPerson.setProjectNo(projectNo);
        signPerson.setApqpNo(apqpNo);
        sign.setProjectNo(projectNo);
        sign.setApqpNo(apqpNo);
        sign.setSignFile(newPDFPath);
        signMapper.insertSignPerson(signPerson);
        fileMapper.save(file);
        fileMapper.updateFileState(file.getProjectNo(), file.getApqpNo());
        System.out.println("service:   " + file.getGmtCreated());
        System.out.println("service:   " + file.getSignFile());

//        signMapper.updateSignFile(sign);
    }

    @Override
    public Integer isUpload(String projectNo, String apqpNo) {
        Integer isUpload = fileMapper.isUpload(projectNo, apqpNo);
        return isUpload;
    }

    @Override
    public List<File> getFileListBeforeStep(String projectNo, String apqpNo) {
        List<File> fileList = fileMapper.getFileListBeforeStep(projectNo, apqpNo);
        return fileList;
    }

    @Override
    public void saveList(List<File> list) {
        fileMapper.saveList(list);
    }

    @Override
    public String getSignFilePath(String projectNo, String apqpNo) {
        String signPath = fileMapper.getSignFilePath(projectNo, apqpNo);
        return signPath;
    }

    @Override
    public Integer isExamined(String projectNo, String apqpNo) {
        Integer isExamined = fileMapper.isExamined(projectNo, apqpNo);
        return isExamined;
    }

    @Override
    public String getFileName(String projectNo, String apqpNo) {
        String fileName = fileMapper.getFileName(projectNo, apqpNo);
        return fileName;
    }
}
