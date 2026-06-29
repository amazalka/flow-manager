package com.example.flowmanager.exception;

public class SaveOutboxException extends RuntimeException {
    public SaveOutboxException(Throwable e) {
        super("Failed to save outbox: " + e);
    }
}
