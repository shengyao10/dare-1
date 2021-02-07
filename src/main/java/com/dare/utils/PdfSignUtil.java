package com.dare.utils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
public class PdfSignUtil {

    private static Logger logger = LoggerFactory.getLogger(PdfSignUtil.class);
    public static boolean deleteFile(String address) {
        File file = new File(address);
        if (!file.exists()) {
            System.out.println("删除失败,文件不存在！");
            return false;
        } else if (file.isFile()) {
            if (file.delete()) {
                System.out.println("删除文件成功！");
                return true;
            } else {
                System.out.println("删除文件失败！");
                return false;
            }
        } else {
            System.out.println("Not a file!");
            return false;
        }
    }

    public static boolean fillPdf(String templatePath, String newPDFPath, Map<Object,Object> treeDto) {
        logger.info ("启动创建PDF方法=============》》》》》》》》》》");
        try {
            logger.info ("创建PDF文件===>>>PDF模板地址==>>"+templatePath);
            logger.info ("创建PDF文件===>>>生成文件地址==>>"+newPDFPath);
            PdfReader reader = new PdfReader(templatePath);
            FileOutputStream out = new FileOutputStream(newPDFPath);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PdfStamper stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();
            Iterator<String> it = form.getFields().keySet().iterator();
            form.addSubstitutionFont(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED));
            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);

            while (it.hasNext()) {
                String name = it.next().toString();
                char ch = name.charAt(0);
                logger.info ("pdf内置表单域信息==》》" + name);
                //写入图片
                if (ch=='n') {
                    //获取图片
                    if(treeDto.get(name)!=null) {
                        Image image = Image.getInstance(treeDto.get(name).toString());
                        int pageNo = form.getFieldPositions(name).get(0).page;
                        Rectangle signRect = form.getFieldPositions(name).get(0).position;
                        float x = signRect.getLeft();
                        float y = signRect.getBottom();
                        PdfContentByte under = stamper.getOverContent(pageNo);
                        image.scaleToFit(signRect.getWidth(), signRect.getHeight());
                        image.setAbsolutePosition(x, y);
                        under.addImage(image);
                    }
                }else {
                    //写入文字
                    if(treeDto.get(name)!=null) {
                        form.setField(name, treeDto.get(name).toString());

                    }
                }

            }
            //生成PDF不可编辑
            stamper.setFormFlattening(true);
            stamper.close();
            doc.open();
            //解决输出PDF只显示一页
            /*PdfImportedPage importPage = null;
            for (int i=1;i<=reader.getNumberOfPages();i++){
                importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), i);
                copy.addPage(importPage);
            }*/
            //如下方法PDF只打一页
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();
            doc.close();
            copy.flush();
            copy.close();
            reader.close();
        } catch (Exception e) {
            logger.debug("创建PDF文件异常=========》》》"+e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /*public static void main(String[] args) {
        String templatePath="D:/会签表.pdf";
        String newPDFPath="D:/会签表0.pdf";
        String newPDFPath1="D:/会签表01.pdf";
        Map<Object, Object> treeDto = new HashMap<>();
        Map<Object, Object> treeDto1 = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String time=formatter.format(date);
        //treeDto.put("name2", "D:/签名3.jpg");
        treeDto.put("time2", time);
        PdfSignUtil.fillPdf(templatePath, newPDFPath, treeDto);
        //treeDto1.put("name3", "D:/签名3.jpg");
        treeDto1.put("time3", time);
        PdfSignUtil.fillPdf(newPDFPath, newPDFPath1, treeDto1);

    }*/
}
