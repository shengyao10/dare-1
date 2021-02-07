package com.dare.utils;

import com.dare.config.MinIoConfig;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @Author: shengyao
 * @Package: com.dare.utils
 * @Date: 2020/10/23 15:16
 * @Description: minIo工具类
 * @version: 1.0
 */
@Slf4j
@Component
@Configuration
public class MinIoUtil {

    private MinIoConfig minIO;

    private static MinioClient minioClient;

    public MinIoUtil(MinIoConfig minIO) {
        this.minIO = minIO;
    }


    /**
     * @param
     * @Author: shengyao
     * @Description: 初始化
     * @Date: 2020/10/23 15:18
     * @Return void
     */
    @PostConstruct
    public void init() {
        minioClient = MinioClient.builder()
                .endpoint(minIO.getEndpoint())
                .credentials(minIO.getAccessKey(), minIO.getSecretKey())
                .build();
    }


    /**
     * @param bucketName 桶名
     * @Author: shengyao
     * @Description: 判断bucket是否存在
     * @Date: 2020/10/23 15:19
     * @Return boolean
     */
    public boolean bucketExists(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }


    /**
     * @param bucketName 桶名
     * @Author: shengyao
     * @Description: 创建桶
     * @Date: 2020/10/23 15:52
     * @Return void
     */
    public void makeBucket(String bucketName) {
        try {
            boolean isExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!isExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * @param
     * @Author: shengyao
     * @Description: 列出所有存储桶的存储信息
     * @Date: 2020/10/24 9:23
     * @Return void
     */
    public void listBuckets() {
        List<Bucket> bucketList = null;
        try {
            bucketList = minioClient.listBuckets();
            for (Bucket bucket : bucketList) {
                System.out.println(bucket.creationDate() + ", " + bucket.name());

            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     * @param bucketName 桶名
     * @Author: shengyao
     * @Description: 列出存储桶的对象信息
     * @Date: 2020/10/24 9:25
     * @Return void
     */
    public void listObject(String bucketName) {
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(bucketName).build());
            for (Result<Item> result : results
            ) {
                Item item = null;
                item = result.get();
                System.out.println(item.lastModified() + ", " + item.size() + ", " + item.objectName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param bucketName 桶名
     * @Author: shengyao
     * @Description: 删除桶
     * @Date: 2020/10/24 9:26
     * @Return void
     */
    public void removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param bucketName 桶名
     * @param objectName 存储桶里的对象名称
     * @param fileName   文件名
     * @Author: shengyao
     * @Description: 文件上传
     * @Date: 2020/10/24 9:27
     * @Return void
     */
    public void uploadObject(String bucketName, String objectName, String fileName) {

        try {
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .filename(fileName)

                            .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param bucketName  桶名
     * @param objectName  存储桶里的对象名称
     * @param stream      要上传的流
     * @param contentType 文件类型
     * @Author: shengyao
     * @Description: 上传文件（流式）
     * @Date: 2020/10/24 10:12
     * @Return void
     */
    public void putObject(String bucketName, String objectName, InputStream stream, String contentType) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(
                            stream, -1, 10485760
                    )
                    .contentType(contentType)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param bucketName 桶名
     * @param objectName 存储桶里的对象名称
     * @Author: shengyao
     * @Description: 删除文件
     * @Date: 2020/10/24 10:14
     * @Return void
     */
    public void removeObject(String bucketName, String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param bucketName 桶名
     * @param objectName 存储桶里的对象名称
     * @param fileName   下载的文件的名称
     * @Author: shengyao
     * @Description: 下载文件
     * @Date: 2020/10/24 10:17
     * @Return void
     */
    public void download(String bucketName, String objectName, String fileName) {
        try {
            minioClient.downloadObject(
                    DownloadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .filename(fileName)
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloadFile(String bucketName, String fileName, HttpServletResponse response) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        InputStream fileStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
        );
        String[] strings = fileName.split("/");
        fileName = strings[strings.length - 1];
        System.out.println(fileName);
        String fileNameCode = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
//        response.setContentType( "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileNameCode);
        ServletOutputStream outputStream = response.getOutputStream();
        // 输出文件
        int length;
        byte[] buffer = new byte[1024];
        while ((length = fileStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        fileStream.close();
        outputStream.close();
    }

    /**
     * @param bucketName 桶名
     * @param objectName 存储桶里的对象名称
     * @Author: shengyao
     * @Description: 获取文件外链
     * @Date: 2020/10/24 10:18
     * @Return String
     */
    public String getObjectUrl(String bucketName, String objectName) {
        try {
//            String url = minioClient.getObjectUrl(bucketName, objectName);
            String url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs
                            .builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
            System.out.println(bucketName + "——" + objectName + " can be downloaded by: " + url);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param bucketName 桶名
     * @param objectName 存储桶里的对象名称
     * @Author: shengyao
     * @Description: 获取对象信息和对象的元数据
     * @Date: 2020/10/24 10:22
     * @Return void
     */
    public void statObject(String bucketName, String objectName) {
        try {
            ObjectStat objectStat = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
            System.out.println(objectStat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean isFileExisted(String fileName, String bucketName) {
        boolean flag = false;
        InputStream inputStream = null;
        try {
            inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build());
            if (inputStream != null) {
                flag = true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            flag = false;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return flag;
    }
}
