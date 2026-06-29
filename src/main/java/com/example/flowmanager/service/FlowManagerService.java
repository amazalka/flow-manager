package com.example.flowmanager.service;

import com.example.flowmanager.exception.FileRequestEntityNotFoundException;
import com.example.flowmanager.exception.FileSizeLimitExceededException;
import com.example.flowmanager.exception.GetOutputPathException;
import com.example.flowmanager.model.client.SubscriptionClient;
import com.example.flowmanager.model.dto.response.DownloadFileResponse;
import com.example.flowmanager.model.dto.response.SubscriptionResponse;
import com.example.flowmanager.model.dto.response.UploadFileResponse;
import com.example.flowmanager.model.entity.*;
import com.example.flowmanager.model.event.InputEvent;
import com.example.flowmanager.repository.FileRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlowManagerService {
    @Value("${subscription.free.max-file-size}")
    private long freeMaxFileSize;

    private final MinioService minioService;
    private final FileRequestService fileRequestService;
    private final OutboxService outboxService;
    private final FileRequestRepository fileRequestRepository;
    private final SubscriptionService subscriptionService;

    @Transactional
    public UploadFileResponse upload(String login, MultipartFile file) {
        validateUpload(login, file);
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

    public ConversionStatus getStatus(UUID id) {
        FileRequestEntity fileRequestEntity = fileRequestRepository.findById(id).orElseThrow();
        return fileRequestEntity.getConversionStatus();
    }

    private void validateUpload (String login, MultipartFile file) {
        SubscriptionResponse subscription = subscriptionService.getSubscription(login);
        if ((subscription.type() == SubscriptionType.FREE) && (file.getSize() > freeMaxFileSize)) {
            throw new FileSizeLimitExceededException();
        }
    }
}

