package com.sunl19ht.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class AliOSSUtils {

    @Value("${cloud.aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${cloud.aliyun.oss.access-key-id}")
    private String accessKeyId;

    @Value("${cloud.aliyun.oss.access-key-secret}")
    private String accessKeySecret;

    @Value("${cloud.aliyun.oss.bucket-name}")
    private String bucketName;

    public String simpleUpload(MultipartFile multipartFile) throws Exception {
        InputStream inputStream = multipartFile.getInputStream();
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")) + multipartFile.getOriginalFilename();
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileName, inputStream);
        String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + fileName;
        ossClient.shutdown();
        return url;
    }
}
