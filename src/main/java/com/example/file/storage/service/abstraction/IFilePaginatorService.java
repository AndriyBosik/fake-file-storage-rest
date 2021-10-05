package com.example.file.storage.service.abstraction;

import com.example.file.storage.document.File;
import com.example.file.storage.model.ResponsePage;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFilePaginatorService {

    ResponsePage<File> getByPage(String name, Pageable pageable);

    ResponsePage<File> getByPage(List<String> tags, String name, Pageable pageable);
}
