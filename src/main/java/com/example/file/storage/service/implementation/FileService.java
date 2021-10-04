package com.example.file.storage.service.implementation;

import com.example.file.storage.document.File;
import com.example.file.storage.repository.IFileRepository;
import com.example.file.storage.service.abstraction.IFileService;
import org.springframework.stereotype.Service;

@Service
public class FileService implements IFileService {

    private final IFileRepository fileRepository;

    public FileService(IFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public String save(File file) {
        return fileRepository.save(file).getId();
    }
}
