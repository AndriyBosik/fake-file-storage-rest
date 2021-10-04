package com.example.file.storage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Beans {

    @Bean
    public Map<String, String[]> getFileTags() {
        Map<String, String[]> fileTags = new HashMap<>();

        fileTags.put("audio", new String[] {"mp3", "wav", "aac", "flac", "pcm", "aiff", "ogg", "wma", "alac"});
        fileTags.put("document", new String[] {"doc", "docx", "html", "htm", "odt", "pdf", "xls", "xlsx", "ods", "ppt", "pptx", "txt"});
        fileTags.put("video", new String[] {"mp4", "mov", "wmv", "avi", "avchd", "mkv", "webm", "mkv"});
        fileTags.put("image", new String[] {"jpg", "png", "gif", "tiff", "psd", "pdf", "eps", "ai", "indd", "raw"});

        return fileTags;
    }
}
