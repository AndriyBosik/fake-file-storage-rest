package com.example.file.storage.repository;

import com.example.file.storage.document.File;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface IFileRepository extends ElasticsearchRepository<File, String> {
}
