package com.droplet.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import com.qcloud.cos.transfer.Upload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class QCloudCOSUtil {
    private static String secretId;

    @Value("${cos.secretId}")
    public void setSecretId(String cosSecretId) {
        secretId = cosSecretId;
    }

    private static String secretKey;

    @Value("${cos.secretKey}")
    public void setSecretKey(String cosSecretKey) {
        secretKey = cosSecretKey;
    }

    private static String region;

    @Value("${cos.region}")
    public void setRegion(String cosRegion) {
        region = cosRegion;
    }

    private static String bucketName;

    @Value("${cos.bucketName}")
    public void setBucketName(String cosBucketName) {
        bucketName = cosBucketName;
    }

    private static TransferManager createTransferManager() {
        COSClient cosClient = createCOSClient();
        ExecutorService threadPool = Executors.newFixedThreadPool(32);
        TransferManager transferManager = new TransferManager(cosClient, threadPool);
        TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
        transferManagerConfiguration.setMultipartUploadThreshold(5 * 1024 * 1024);
        transferManagerConfiguration.setMinimumUploadPartSize(1024 * 1024);
        transferManager.setConfiguration(transferManagerConfiguration);
        return transferManager;
    }

    private static void shutdownTransferManager(TransferManager transferManager) {
        transferManager.shutdownNow(true);
    }

    private static COSClient createCOSClient() {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setRegion(new Region(region));
        clientConfig.setHttpProtocol(HttpProtocol.https);
        clientConfig.setSocketTimeout(30 * 1000);
        clientConfig.setConnectionTimeout(30 * 1000);
        return new COSClient(cred, clientConfig);
    }

    public static String uploadFileTest(File file, String filePath) {
        TransferManager transferManager = createTransferManager();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, file);
        try {
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
        } catch (CosClientException | InterruptedException e) {
            e.printStackTrace();
        }
        shutdownTransferManager(transferManager);
        return "https://droplet-blog-1312112430.cos.ap-shanghai.myqcloud.com/" + filePath;
    }
}
