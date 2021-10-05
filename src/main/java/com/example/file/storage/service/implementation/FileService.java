package com.example.file.storage.service.implementation;

import com.example.file.storage.document.File;
import com.example.file.storage.tag.abstraction.ITagResolver;
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
    private final ITagResolver tagResolver;

    public FileService(
        IFileRepository fileRepository,
        IExtendedFileRepository extendedFileRepository,
        ITagResolver tagResolver
    ) {
        this.fileRepository = fileRepository;
        this.extendedFileRepository = extendedFileRepository;
        this.tagResolver = tagResolver;
    }

    @Override
    public String save(File file) {
        String fileTag = tagResolver.getTagByExtension(file.getName());
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
