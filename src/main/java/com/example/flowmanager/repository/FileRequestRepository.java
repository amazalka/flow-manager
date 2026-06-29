package com.example.flowmanager.repository;

import com.example.flowmanager.model.entity.FileRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileRequestRepository extends JpaRepository<FileRequestEntity, UUID> {
}
