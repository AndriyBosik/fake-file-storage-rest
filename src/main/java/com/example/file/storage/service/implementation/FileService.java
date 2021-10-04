package com.example.file.storage.service.implementation;

import com.example.file.storage.document.File;
import com.example.file.storage.tag.abstraction.IFileTagHandler;
import com.example.file.storage.repository.abstraction.IExtendedFileRepository;
import com.example.file.storage.repository.abstraction.IFileRepository;
import com.example.file.storage.service.abstraction.IFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FileService implements IFileService {

    private final IFileRepository fileRepository;
    private final IExtendedFileRepository extendedFileRepository;
    private final IFileTagHandler fileTagHandler;

    public FileService(
        IFileRepository fileRepository,
        IExtendedFileRepository extendedFileRepository,
        IFileTagHandler fileTagHandler
    ) {
        this.fileRepository = fileRepository;
        this.extendedFileRepository = extendedFileRepository;
        this.fileTagHandler = fileTagHandler;
    }

    @Override
    public String save(File file) {
        String fileTag = fileTagHandler.getTag(file.getName());
        if (fileTag != null) {
            file.getTags().add(fileTag);
        }
        return fileRepository.save(file).getId();
    }

    @Transactional
    @Override
    public boolean delete(String id) {
        return extendedFileRepository.deleteFile(id);
    }

    @Override
    public void updateFileTags(String id, List<String> tags) throws Exception {
        extendedFileRepository.updateFileTags(id, tags);
    }

    @Override
    public void deleteFileTags(String id, List<String> tags) throws Exception {
        extendedFileRepository.deleteFileTags(id, tags);
    }
}
