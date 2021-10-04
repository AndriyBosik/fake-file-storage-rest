package com.example.file.storage.service.implementation;

import com.example.file.storage.document.File;
import com.example.file.storage.mapper.IMapper;
import com.example.file.storage.model.ResponsePage;
import com.example.file.storage.repository.abstraction.IExtendedFileRepository;
import com.example.file.storage.repository.abstraction.IFileRepository;
import com.example.file.storage.service.abstraction.IFileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FileService implements IFileService {

    private final IFileRepository fileRepository;
    private final IExtendedFileRepository extendedFileRepository;
    private final IMapper<Page<File>, ResponsePage<File>> mapper;

    public FileService(
        IFileRepository fileRepository,
        IExtendedFileRepository extendedFileRepository,
        IMapper<Page<File>, ResponsePage<File>> mapper
    ) {
        this.fileRepository = fileRepository;
        this.extendedFileRepository = extendedFileRepository;
        this.mapper = mapper;
    }

    @Override
    public String save(File file) {
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

    @Override
    public ResponsePage<File> getByPage(Pageable pageable) {
        return mapper.map(fileRepository.findAll(pageable));
    }

    @Override
    public ResponsePage<File> getByPage(List<String> tags, Pageable pageable) {
        return mapper.map(fileRepository.findAllByTags(tags, pageable));
    }
}
