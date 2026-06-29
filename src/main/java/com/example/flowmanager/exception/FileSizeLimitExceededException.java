package com.example.flowmanager.exception;

public class FileSizeLimitExceededException extends RuntimeException {
    public FileSizeLimitExceededException() {
        super("File size limit exceeded");
    }
}
