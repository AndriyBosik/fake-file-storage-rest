package com.example.file.storage.service.implementation;

import com.example.file.storage.document.File;
import com.example.file.storage.mapper.IMapper;
import com.example.file.storage.model.ResponsePage;
import com.example.file.storage.repository.abstraction.IExtendedFileRepository;
import com.example.file.storage.repository.abstraction.IFileRepository;
import com.example.file.storage.service.abstraction.IFilePaginatorService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilePaginatorService implements IFilePaginatorService {

    private final IFileRepository fileRepository;
    private final IExtendedFileRepository extendedFileRepository;
    private final IMapper<SearchPage<File>, ResponsePage<File>> mapper;

    public FilePaginatorService(
        IFileRepository fileRepository,
        IExtendedFileRepository extendedFileRepository,
        IMapper<SearchPage<File>, ResponsePage<File>> mapper
    ) {
        this.fileRepository = fileRepository;
        this.extendedFileRepository = extendedFileRepository;
        this.mapper = mapper;
    }

    @Override
    public ResponsePage<File> getByPage(String name, Pageable pageable) {
        return mapper.map(extendedFileRepository.getFilesByName(name, pageable));
    }

    @Override
    public ResponsePage<File> getByPage(List<String> tags, String name, Pageable pageable) {
        return mapper.map(extendedFileRepository.getFilesByNameAndTags(name, tags, pageable));
    }
}
