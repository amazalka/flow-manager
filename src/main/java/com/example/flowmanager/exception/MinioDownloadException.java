package com.example.flowmanager.exception;

public class MinioDownloadException extends RuntimeException {
    public MinioDownloadException(String path) {
        super("Failed to download file to MinIO: " + path);
    }
}
