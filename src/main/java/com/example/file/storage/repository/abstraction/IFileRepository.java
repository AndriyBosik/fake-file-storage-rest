package com.example.file.storage.repository.abstraction;

import com.example.file.storage.document.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface IFileRepository extends ElasticsearchRepository<File, String> {

    Page<File> findAllByTagsAndNameContaining(List<String> tags, String name, Pageable pageable);

    Page<File> findAllByNameContaining(String name, Pageable pageable);
}
