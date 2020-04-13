package com.baizhi.zw.test;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.List;

public class TestAliyunOss {
    private String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，
    // 请登录 https://ram.console.aliyun.com 创建RAM账号。
    private String accessKeyId = "LTAI4FtmnnJMcXyeJroyNe8y";
    private String accessKeySecret = "unjo5wFUFBR2B94cG748VPVffuCf3d";

    @Test  //创建存储空间
    public void createBucket() {
        String bucketName = "22222ddd33";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建存储空间。
        ossClient.createBucket(bucketName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test //列举所有存储空间
    public void queryAllBuckets() {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 列举存储空间。
        List<Bucket> buckets = ossClient.listBuckets();
        for (Bucket bucket : buckets) {
            System.out.println(" - " + bucket.getName());
        }

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test //判断存储空间是否存在
    public void exists() {
        String bucketName = "yingx-zw";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        boolean exists = ossClient.doesBucketExist(bucketName);
        System.out.println(exists);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test //删除存储空间
    public void deleteBucket() {

        String bucketName = "22222ddd33";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除存储空间。
        ossClient.deleteBucket(bucketName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test //下载文件
    public void downLoad() {

        String bucketName = "yingx-zw";
        String objectName = "2.jpg";
        //下载文件位置
        String localFile = "C:\\Users\\zhaowei\\Pictures" + objectName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFile));

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test //上传本地文件
    public void uploadLocalFile() {

        String bucketName = "yingx-zw";
        String objectName = "2.jpg";
        String localFile = "C:\\Users\\zhaowei\\Pictures\\300x200\\2.jpg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new File(localFile));

        // 上传文件。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();

    }

    @Test //上传文件(Byte数组)
    public void uploadFileByte() {

        String bucketName = "yingx-zw";
        String objectName = "2.text";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传Byte数组。
        byte[] content = "Hello OSS".getBytes();
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content));

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test //上传文件流
    public void uploadInputStream() throws Exception {

        String bucketName = "yingx-zw";  //存储空间名
        String objectName = "aaa.jpg";  //文件名  可以指定文件目录
        String localFile = "C:\\Users\\zhaowei\\Pictures\\300x200\\2.jpg";  //本地文件路径

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = new FileInputStream(localFile);
        //上传文件
        ossClient.putObject(bucketName, objectName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test // 上传网络流。
    public void uploadNetStream() {
        String bucketName = "yingx-zw";  //存储空间名
        String objectName = "aaa.jpg";  //文件名  可以指定文件目录

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        InputStream inputStream = null;
        try {
            inputStream = new URL("https://www.aliyun.com/").openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ossClient.putObject(bucketName, objectName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test //删除文件
    public void deleteFile() {

        String bucketName = "yingx-zw";
        String objectName = "2.jpg";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();

    }

    @Test //视频截取
    public void interceptVideo() {

        String bucketName = "yingx-zw";
        String objectName = "video/旅行.mp4";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 设置视频截帧操作。
        String style = "video/snapshot,t_3000,f_jpg,w_0,h_0";
        // 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10);
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        System.out.println(signedUrl);
        // 关闭OSSClient。
        ossClient.shutdown();

    }

}
