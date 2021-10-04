package com.example.file.storage.service.abstraction;

import com.example.file.storage.document.File;

import java.util.List;

public interface IFileService {

    String save(File file);

    boolean delete(String id);

    void updateFileTags(String id, List<String> tags) throws Exception;

    void deleteFileTags(String id, List<String> tags) throws Exception;
}
