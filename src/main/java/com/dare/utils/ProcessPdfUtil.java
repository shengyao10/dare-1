package com.dare.utils;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
public class ProcessPdfUtil {
    //测试主函数
    public static void main(String[] args) {
        try {
            List<InputStream> pdfs = new ArrayList<InputStream>();
            pdfs.add(new FileInputStream("d:/0.3.2 立项-会议记录.pdf"));
            pdfs.add(new FileInputStream("d:/0.3.2 立项-会议记录1.pdf"));
            OutputStream output = new FileOutputStream("d:/0.3.2 立项-会议记录2.pdf");
            concatPDFs(pdfs, output, true);

            ProcessPdfUtil.addPdfImgMark("d:/0.3.2 立项-会议记录.pdf",
                    "d:/0.3.2 立项-会议记录1.pdf",
                    "d:/梁伟业.jpg",
                    200,350,1);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //合并pdf
    public static void concatPDFs(List<InputStream> streamOfPDFFiles,
                                  OutputStream outputStream, boolean paginate) {

        Document document = new Document();
        try {
            List<InputStream> pdfs = streamOfPDFFiles;
            List<PdfReader> readers = new ArrayList<PdfReader>();
            int totalPages = 0;
            Iterator<InputStream> iteratorPDFs = pdfs.iterator();

            // Create Readers for the pdfs.
            while (iteratorPDFs.hasNext()) {
                InputStream pdf = iteratorPDFs.next();
                PdfReader pdfReader = new PdfReader(pdf);
                readers.add(pdfReader);
                totalPages += pdfReader.getNumberOfPages();
            }
            // Create a writer for the outputstream
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            document.open();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
                    BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            PdfContentByte cb = writer.getDirectContent(); // Holds the PDF
            // data

            PdfImportedPage page;
            int currentPageNumber = 0;
            int pageOfCurrentReaderPDF = 0;
            Iterator<PdfReader> iteratorPDFReader = readers.iterator();

            // Loop through the PDF files and add to the output.
            while (iteratorPDFReader.hasNext()) {
                PdfReader pdfReader = iteratorPDFReader.next();

                // Create a new page in the target for each source page.
                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
                    document.newPage();
                    pageOfCurrentReaderPDF++;
                    currentPageNumber++;
                    page = writer.getImportedPage(pdfReader,
                            pageOfCurrentReaderPDF);
                    cb.addTemplate(page, 0, 0);

                    // Code for pagination.
                    if (paginate) {
                        cb.beginText();
                        cb.setFontAndSize(bf, 9);
                        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, ""
                                        + currentPageNumber + " of " + totalPages, 520,
                                5, 0);
                        cb.endText();
                    }
                }
                pageOfCurrentReaderPDF = 0;
            }
            outputStream.flush();
            document.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen())
                document.close();
            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }


    //pdf水印
    public static void addPdfImgMark(String InPdfFile, String outPdfFile, String markImagePath,int imgWidth, int imgHeight,int pagenum) throws Exception {
        PdfReader reader = new PdfReader(InPdfFile, "PDF".getBytes());
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(new File(outPdfFile)));

        PdfContentByte under;

        PdfGState gs1 = new PdfGState();
        gs1.setFillOpacity(0.3f);// 透明度设置

        Image img = Image.getInstance(markImagePath);// 插入图片水印

        img.setAbsolutePosition(imgWidth, imgHeight); // 坐标
        img.setRotation(0);// 旋转 弧度
        img.setRotationDegrees(0);// 旋转 角度
        img.scaleAbsolute(40,20);//自定义大小
        // img.scalePercent(50);//依照比例缩放


        under = stamp.getUnderContent(pagenum);// 水印在之前文本下
        // under = stamp.getOverContent(i);//水印在之前文本上
        under.setGState(gs1);// 图片水印 透明度
        under.addImage(img);// 图片水印

        stamp.close();// 关闭
        // // 删除不带水印文件
//		File tempfile = new File(InPdfFile);
//		if(tempfile.exists()) {
//			tempfile.delete();
//		}

    }
}
