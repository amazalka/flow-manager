package com.example.flowmanager.exception;

public class GetOutputPathException extends RuntimeException {
    public GetOutputPathException() {
        super("Output path not found");
    }
}
