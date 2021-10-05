package com.example.file.storage.repository.abstraction;

import com.example.file.storage.document.File;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;

import java.util.List;

public interface IExtendedFileRepository {

    void updateFileTags(String id, List<String> tags) throws Exception;

    void deleteFileTags(String id, List<String> tags) throws Exception;

    boolean deleteFile(String id);

    SearchPage<File> getFilesByName(String name, Pageable pageable);

    SearchPage<File> getFilesByNameAndTags(String name, List<String> tags, Pageable pageable);
}
