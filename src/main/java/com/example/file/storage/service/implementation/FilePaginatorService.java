package com.example.file.storage.service.implementation;

import com.example.file.storage.document.File;
import com.example.file.storage.mapper.IMapper;
import com.example.file.storage.model.ResponsePage;
import com.example.file.storage.repository.abstraction.IFileRepository;
import com.example.file.storage.service.abstraction.IFilePaginatorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilePaginatorService implements IFilePaginatorService {

    private final IFileRepository fileRepository;
    private final IMapper<Page<File>, ResponsePage<File>> mapper;

    public FilePaginatorService(IFileRepository fileRepository, IMapper<Page<File>, ResponsePage<File>> mapper) {
        this.fileRepository = fileRepository;
        this.mapper = mapper;
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
