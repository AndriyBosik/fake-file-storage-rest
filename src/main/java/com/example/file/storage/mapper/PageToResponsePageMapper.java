package com.example.file.storage.mapper;

import com.example.file.storage.model.ResponsePage;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageToResponsePageMapper<T> implements IMapper<Page<T>, ResponsePage<T>> {

    @Override
    public ResponsePage<T> map(Page<T> model) {
        return new ResponsePage<>(
            model.getTotalElements(),
            model.getContent()
        );
    }
}
