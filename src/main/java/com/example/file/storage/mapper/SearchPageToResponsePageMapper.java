package com.example.file.storage.mapper;

import com.example.file.storage.document.File;
import com.example.file.storage.model.ResponsePage;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SearchPageToResponsePageMapper<T> implements IMapper<SearchPage<T>, ResponsePage<T>> {

    @Override
    public ResponsePage<T> map(SearchPage<T> model) {
        return new ResponsePage<>(
            model.getTotalElements(),
            model.getContent().stream().map(SearchHit::getContent).collect(Collectors.toList())
        );
    }
}
