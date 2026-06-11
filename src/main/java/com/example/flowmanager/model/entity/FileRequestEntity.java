package com.example.flowmanager.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "file-request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileRequestEntity {
    @Id
    @Column(name = "file_id")
    private UUID fileId;
    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private FileType fileType;
    @Column(name = "input_path")
    private String inputPath;
    @Column(name = "output_path")
    private String outputPath;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ConversionStatus conversionStatus;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
