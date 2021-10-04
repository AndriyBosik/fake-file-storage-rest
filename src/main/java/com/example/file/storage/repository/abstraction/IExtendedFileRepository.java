package com.example.file.storage.repository.abstraction;

import java.util.List;

public interface IExtendedFileRepository {

    void updateFileTags(String id, List<String> tags) throws Exception;

    void deleteFileTags(String id, List<String> tags) throws Exception;

    boolean deleteFile(String id);
}
