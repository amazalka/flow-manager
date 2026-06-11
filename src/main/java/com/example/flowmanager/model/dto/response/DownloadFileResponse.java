package com.example.flowmanager.model.dto.response;

import com.example.flowmanager.model.entity.FileType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloadFileResponse {
    private InputStream inputStream;
    private String filePath;
    private FileType type;
}
