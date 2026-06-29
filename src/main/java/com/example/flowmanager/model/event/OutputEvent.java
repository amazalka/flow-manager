package com.example.flowmanager.model.event;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputEvent {
    private UUID eventId;
    private String filePath;
}
