package com.example.file.storage.mapper;

import com.example.file.storage.model.ResponsePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class SearchPageToResponsePageMapperTest {

    private static final int total = 5;
    private static final String[] elements = new String[] {"elem 1", "elem 2", "elem 3"};
    private static final SearchPageToResponsePageMapper<String> mapper = new SearchPageToResponsePageMapper<>();

    private static SearchPage<String> page;

    @BeforeAll
    public static void beforeAll() {
        page = new SearchPage<>() {
            @Override
            public SearchHits<String> getSearchHits() {
                return null;
            }

            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return total;
            }

            @Override
            public <U> Page<U> map(Function<? super SearchHit<String>, ? extends U> converter) {
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
            public List<SearchHit<String>> getContent() {
                List<SearchHit<String>> hits = new ArrayList<>();
                for (String element: elements) {
                    SearchHit<String> hit = (SearchHit<String>) Mockito.mock(SearchHit.class);
                    Mockito.when(hit.getContent()).thenReturn(element);
                    hits.add(hit);
                }
                return hits;
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
            public Iterator<SearchHit<String>> iterator() {
                return null;
            }
        };
    }

    @Test
    public void shouldMap() {
        ResponsePage<String> responsePage = mapper.map(page);

        assertEquals(total, responsePage.getTotal());
        assertEquals(elements.length, responsePage.getPage().size());
        for (int i = 0; i < elements.length; i++) {
            assertEquals(elements[i], responsePage.getPage().get(i));
        }
    }
}
