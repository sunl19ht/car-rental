package com.sunl19ht.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class TencentCOSUtils {

    @Value("${cloud.tencent.cos.secret-id}")
    private String tmpSecretId;

    @Value("${cloud.tencent.cos.secret-key}")
    private String tmpSecretKey;

    @Value("${cloud.tencent.cos.region}")
    private String bucket;

    @Value("${cloud.tencent.cos.bucket-name}")
    private String bucketName;

    public COSClient createClient() {
        COSCredentials cred = new BasicCOSCredentials(tmpSecretId, tmpSecretKey);
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setRegion(new Region(bucket));
        return new COSClient(cred, clientConfig);
    }

    public String simpleUpload(MultipartFile multipartFile) throws IOException {
        COSClient cosClient = createClient();
        String key = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")) + "-" + multipartFile.getOriginalFilename();
        InputStream inputStream = multipartFile.getInputStream();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, null);
        putObjectRequest.setStorageClass(StorageClass.Standard_IA);

        try {
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            log.info("COS 上传成功, Request ID: {}", putObjectResult.getRequestId());
        } catch (CosServiceException e) {
            log.error("COS 服务异常: {}", e.getMessage(), e);
            throw e;
        } catch (CosClientException e) {
            log.error("COS 客户端异常: {}", e.getMessage(), e);
            throw e;
        } finally {
            cosClient.shutdown();
        }
        return key;
    }
}
