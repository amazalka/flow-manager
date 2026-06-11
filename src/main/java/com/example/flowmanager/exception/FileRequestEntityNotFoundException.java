package com.example.flowmanager.exception;

import java.util.UUID;

public class FileRequestEntityNotFoundException extends RuntimeException {
    public FileRequestEntityNotFoundException(UUID id) {
        super("File with id = " + id + " not found");
    }
}
