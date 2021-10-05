package com.example.file.storage.repository.implementation;

import com.example.file.storage.config.Indexes;
import com.example.file.storage.document.File;
import com.example.file.storage.repository.abstraction.IExtendedFileRepository;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

@Repository
public class ExtendedFileRepository implements IExtendedFileRepository {

    private final RestHighLevelClient client;
    private final ElasticsearchOperations operations;

    @Autowired
    public ExtendedFileRepository(
        RestHighLevelClient client,
        ElasticsearchOperations operations
    ) {
        this.client = client;
        this.operations = operations;
    }

    @Override
    public void updateFileTags(String id, List<String> tags) throws Exception {
        UpdateRequest request = new UpdateRequest(Indexes.FILES, id).doc("tags", tags);
        client.update(request, RequestOptions.DEFAULT);
    }

    @Override
    public void deleteFileTags(String id, List<String> tags) throws Exception {
        UpdateRequest request = new UpdateRequest(Indexes.FILES, id);
        Map<String, Object> params = new HashMap<>();
        params.put("tags", tags);
        request.script(new Script(
            ScriptType.INLINE,
            "painless",
            "for (String tag: params.tags) {" +
                "ctx._source.tags.remove(ctx._source.tags.indexOf(tag))" +
            "}",
            params
        ));
        client.update(request, RequestOptions.DEFAULT);
    }

    @Override
    public boolean deleteFile(String id) {
        DeleteRequest request = new DeleteRequest(Indexes.FILES, id);
        try {
            DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
            return response.getResult() == DocWriteResponse.Result.DELETED;
        } catch (IOException exception) {
            return false;
        }
    }

    @Override
    public SearchPage<File> getFilesByName(String name, Pageable pageable) {
        Query query = new NativeSearchQueryBuilder()
            .withQuery(getNameQueryBuilder(name))
            .withPageable(pageable)
            .build();

        return extractFilesPageByQuery(query);
    }

    @Override
    public SearchPage<File> getFilesByNameAndTags(String name, List<String> tags, Pageable pageable) {
        BoolQueryBuilder containsTagsQuery = QueryBuilders.boolQuery();
        for (String tag: tags) {
            containsTagsQuery.must(QueryBuilders.termQuery("tags", tag));
        }
        Query query = new NativeSearchQueryBuilder()
                .withQuery(
                    QueryBuilders.boolQuery()
                        .must(containsTagsQuery)
                        .must(getNameQueryBuilder(name)))
                .withPageable(pageable)
                .build();

        return extractFilesPageByQuery(query);
    }

    private SearchPage<File> extractFilesPageByQuery(Query query) {
        SearchHits<File> hits = operations.search(query, File.class);
        return SearchHitSupport.searchPageFor(hits, query.getPageable());
    }

    private QueryBuilder getNameQueryBuilder(String name) {
        name = name.replaceAll(" ", "\\\\ ");
        String queryString = "name:*" + name + "*";
        return QueryBuilders.queryStringQuery(queryString);
    }
}
