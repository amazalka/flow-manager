package com.example.flowmanager.service;

import com.example.flowmanager.exception.FileRequestEntityNotFoundException;
import com.example.flowmanager.exception.GetOutputPathException;
import com.example.flowmanager.model.dto.response.DownloadFileResponse;
import com.example.flowmanager.model.dto.response.UploadFileResponse;
import com.example.flowmanager.model.entity.ConversionStatus;
import com.example.flowmanager.model.entity.EventType;
import com.example.flowmanager.model.entity.FileRequestEntity;
import com.example.flowmanager.model.entity.FileType;
import com.example.flowmanager.model.event.InputEvent;
import com.example.flowmanager.repository.FileRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlowManagerService {
    private final MinioService minioService;
    private final FileRequestService fileRequestService;
    private final OutboxService outboxService;
    private final FileRequestRepository fileRequestRepository;

    @Transactional
    public UploadFileResponse upload(MultipartFile file) {
        UUID uuid = UUID.randomUUID();
        String path = minioService.upload(uuid, file);
        fileRequestService.create(uuid, path, FileType.fromMimeType(file.getContentType()));
        InputEvent inputEvent = new InputEvent(uuid, path, FileType.fromMimeType(file.getContentType()));
        outboxService.saveEvent(EventType.FILE_UPLOADED, inputEvent);
        return new UploadFileResponse(uuid);
    }

    public DownloadFileResponse download(UUID id) {
        FileRequestEntity fileRequestEntity = fileRequestRepository.findById(id).orElseThrow(() -> new FileRequestEntityNotFoundException(id));
        if (fileRequestEntity.getOutputPath() == null) {
            throw new GetOutputPathException();
        }
        InputStream inputStream = minioService.download(fileRequestEntity.getOutputPath());
        return new DownloadFileResponse(inputStream, fileRequestEntity.getOutputPath(), fileRequestEntity.getFileType());
    }

    public ConversionStatus returnStatus(UUID id) {
        FileRequestEntity fileRequestEntity = fileRequestRepository.findById(id).orElseThrow();
        return fileRequestEntity.getConversionStatus();
    }
}

