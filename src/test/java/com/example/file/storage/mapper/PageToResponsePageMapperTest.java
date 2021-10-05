package com.example.file.storage.mapper;

import com.example.file.storage.model.ResponsePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class PageToResponsePageMapperTest {

    private static final int TOTAL_ELEMENTS = 23;
    private static final String[] CONTENT = new String[] {
        "element 1", "element 2", "element 3", "element 4", "element 5"
    };

    private static Page<String> page;
    private static PageToResponsePageMapper<String> mapper;

    @BeforeAll
    public static void beforeAll() {
        mapper = new PageToResponsePageMapper<>();

        page = new Page<>() {
            @Override
            public int getTotalPages() {
                return 10;
            }

            @Override
            public long getTotalElements() {
                return TOTAL_ELEMENTS;
            }

            @Override
            public <U> Page<U> map(Function<? super String, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<String> getContent() {
                return Arrays.asList(CONTENT);
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<String> iterator() {
                return null;
            }
        };
    }

    @Test
    public void shouldMap() {
        ResponsePage<String> responsePage = mapper.map(page);

        assertEquals(TOTAL_ELEMENTS, responsePage.getTotal());
        for (int i = 0; i < CONTENT.length; i++) {
            assertEquals(CONTENT[i], responsePage.getPage().get(i));
        }
    }
}
