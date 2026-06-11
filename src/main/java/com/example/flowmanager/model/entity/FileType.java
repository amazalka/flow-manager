package com.example.flowmanager.model.entity;

import java.util.Arrays;

public enum FileType {
    TXT, PNG, JPG, ZIP, PDF;

    public static FileType fromMimeType(String mimeType) {
        return switch (mimeType) {
            case "text/plain" -> TXT;
            case "image/png" -> PNG;
            case "image/jpeg" -> JPG;
            case "application/zip" -> ZIP;
            case "application/pdf" -> PDF;
            default -> throw new IllegalArgumentException("Unsupported mime type: " + mimeType);
        };
    }
}
