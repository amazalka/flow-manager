package com.example.flowmanager.service;

import com.example.flowmanager.model.entity.ConversionStatus;
import com.example.flowmanager.model.entity.FileRequestEntity;
import com.example.flowmanager.model.entity.FileType;
import com.example.flowmanager.repository.FileRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileRequestService {
    private final FileRequestRepository fileRequestRepository;

    @Transactional
    public FileRequestEntity create(UUID id, String inputFile, FileType type) {
        return fileRequestRepository.save(new FileRequestEntity(id, type, inputFile, null, ConversionStatus.PROCESSING, LocalDateTime.now(), LocalDateTime.now()));
    }

    public void markSuccess(UUID id, String outputPath) {
        fileRequestRepository.findById(id).ifPresent(fileRequestEntity ->
        {
            fileRequestEntity.setFileType(FileType.PDF);
            fileRequestEntity.setOutputPath(outputPath);
            fileRequestEntity.setUpdatedAt(LocalDateTime.now());
            fileRequestEntity.setConversionStatus(ConversionStatus.SUCCESSFUL);
            fileRequestRepository.save(fileRequestEntity);
        });
    }

    public void markFailed(UUID id) {
        fileRequestRepository.findById(id).ifPresent(fileRequestEntity ->
        {
            fileRequestEntity.setUpdatedAt(LocalDateTime.now());
            fileRequestEntity.setConversionStatus(ConversionStatus.ERROR);
            fileRequestRepository.save(fileRequestEntity);
        });
    }
}
