package com.example.flowmanager.model.dto.response;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileResponse {
    private UUID uuid;
}
