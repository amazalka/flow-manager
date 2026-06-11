package com.example.flowmanager.controller;

import com.example.flowmanager.model.dto.response.DownloadFileResponse;
import com.example.flowmanager.model.dto.response.UploadFileResponse;
import com.example.flowmanager.model.entity.ConversionStatus;
import com.example.flowmanager.service.FlowManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/flow-manager")
public class FlowManagerController {
    private final FlowManagerService flowManagerService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadFileResponse upload(@RequestParam("file") MultipartFile file) {
        return flowManagerService.upload(file);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<InputStreamResource> download(@PathVariable UUID id) {
        DownloadFileResponse file = flowManagerService.download(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "filename=" + Paths.get(file.getFilePath()).getFileName().toString())
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(file.getInputStream()));
    }

    @GetMapping("/files/{id}/status")
    public ConversionStatus returnStatus(@PathVariable UUID id) {
        return flowManagerService.returnStatus(id);
    }
}
