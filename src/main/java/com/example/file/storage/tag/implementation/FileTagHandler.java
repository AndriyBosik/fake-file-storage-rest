package com.example.file.storage.tag.implementation;

import com.example.file.storage.tag.abstraction.IFileTagHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
public class FileTagHandler implements IFileTagHandler {

    private final Map<String, String[]> fileTags;

    @Autowired
    public FileTagHandler(Map<String, String[]> fileTags) {
        this.fileTags = fileTags;
    }

    @Override
    public String getTag(String filename) {
        String fileExtension = getFileExtension(filename);
        for (Map.Entry<String, String[]> entry: fileTags.entrySet()) {
            if (Arrays.stream(entry.getValue()).anyMatch(extension -> extension.equalsIgnoreCase(fileExtension))) {
                return entry.getKey();
            }
        }
        return null;
    }

    private String getFileExtension(String filename) {
        String[] parts = filename.split("\\.");
        if (parts.length == 0) {
            return "";
        }
        return parts[parts.length - 1];
    }
}
