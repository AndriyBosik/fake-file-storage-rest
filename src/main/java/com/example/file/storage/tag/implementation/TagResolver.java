package com.example.file.storage.tag.implementation;

import com.example.file.storage.tag.abstraction.ITagResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
public class TagResolver implements ITagResolver {

    private final Map<String, String[]> tagToExtensions;

    @Autowired
    public TagResolver(Map<String, String[]> tagToExtensions) {
        this.tagToExtensions = tagToExtensions;
    }

    @Override
    public String getTagByExtension(String filename) {
        String fileExtension = getFileExtension(filename);
        for (Map.Entry<String, String[]> entry: tagToExtensions.entrySet()) {
            if (containsExtension(entry.getValue(), fileExtension)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private boolean containsExtension(String[] extensions, String fileExtension) {
        return Arrays.stream(extensions)
            .anyMatch(extension -> extension.equalsIgnoreCase(fileExtension));
    }

    private String getFileExtension(String filename) {
        String[] parts = filename.split("\\.");
        if (parts.length == 0) {
            return "";
        }
        return parts[parts.length - 1];
    }
}
