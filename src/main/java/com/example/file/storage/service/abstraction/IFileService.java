package com.example.file.storage.service.abstraction;

import com.example.file.storage.document.File;
import com.example.file.storage.model.ResponsePage;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFileService {

    String save(File file);

    boolean delete(String id);

    void updateFileTags(String id, List<String> tags) throws Exception;

    void deleteFileTags(String id, List<String> tags) throws Exception;

    ResponsePage<File> getByPage(Pageable pageable);

    ResponsePage<File> getByPage(List<String> tags, Pageable pageable);
}
