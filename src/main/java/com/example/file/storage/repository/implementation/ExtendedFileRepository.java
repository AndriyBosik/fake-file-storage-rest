package com.example.file.storage.repository.implementation;

import com.example.file.storage.config.Indexes;
import com.example.file.storage.repository.abstraction.IExtendedFileRepository;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

@Repository
public class ExtendedFileRepository implements IExtendedFileRepository {

    private final RestHighLevelClient client;

    @Autowired
    public ExtendedFileRepository(RestHighLevelClient client) {
        this.client = client;
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
}
