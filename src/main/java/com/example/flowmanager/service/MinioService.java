package com.example.flowmanager.service;

import com.example.flowmanager.exception.MinioDownloadException;
import com.example.flowmanager.exception.MinioUploadException;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioService {
    private final MinioClient minioClient;
    @Value("${flow-manager.minio.bucket}")
    private String bucket;

    public String upload(UUID uuid, MultipartFile file) {
        try {
            String path = "input/" + uuid + "_" + file.getOriginalFilename();
            minioClient.putObject(PutObjectArgs.builder().
                    bucket(bucket)
                    .object(path)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
            return path;
        } catch (Exception e) {
            throw new MinioUploadException(file.getOriginalFilename());
        }
    }

    public InputStream download(String path) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(path)
                            .build()
            );
        } catch (Exception e) {
            throw new MinioDownloadException(path);
        }
    }
}
