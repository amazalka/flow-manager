package com.example.flowmanager.exception;

public class MinioUploadException extends RuntimeException {
    public MinioUploadException(String path) {
        super("Failed to upload file to MinIO: " + path);
    }
}
