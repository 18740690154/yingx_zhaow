package com.baizhi.zw.util;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

public class AliyunOssUtil {
    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "LTAI4FtmnnJMcXyeJroyNe8y";
    private static String accessKeySecret = "unjo5wFUFBR2B94cG748VPVffuCf3d";
    private static String bucketName = "yingx-zw";

    /* 本地文件上传：
        参数: MultipartFile file:
              String bucketName:存储空间名
              String fileName: 指定上传文件名和上传目录  文件目录/文件名
              String localFilePath: 指定上传的本地文件路径
     */
    public static void uploadFileLocal(String fileName, String localFilePath) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, new File(localFilePath));

        // 上传文件。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /* 文件上传(byte数组)：
        参数: MultipartFile file:指定需要上传的MultipartFile类型的文件
              String bucketName:存储空间名
              String fileName: 指定上传文件名和上传目录
     */
    public static void uploadFileBytes(MultipartFile file, String fileName) {

        //将文件转化为字节数组
        byte[] bytes = null;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传Byte数组。
        ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(bytes));

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //删除文件
    public static void deleteFile(String fileName) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, fileName);

        // 关闭OSSClient。
        ossClient.shutdown();

    }

    //视频截取
    public static void interceptVideo(String fileName,String coverName) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 设置视频截帧操作。
        String style = "video/snapshot,t_50000,f_jpg,w_0,h_0";
        // 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10);
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, fileName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        System.out.println(signedUrl);

        //文件上传-----网络流
        InputStream inputStream = null;
        try {
            inputStream = new URL(signedUrl.toString()).openStream();
            ossClient.putObject(bucketName, coverName, inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
