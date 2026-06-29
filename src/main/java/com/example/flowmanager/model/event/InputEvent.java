package com.example.flowmanager.model.event;
import com.example.flowmanager.model.entity.FileType;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputEvent {
    private UUID eventId;
    private String filePath;
    private FileType fileType;
}
