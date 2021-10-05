package com.example.file.storage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Beans {

    @Bean
    public Map<String, String[]> getTagToExtensions() {
        Map<String, String[]> tagToExtensions = new HashMap<>();

        tagToExtensions.put("audio", new String[] {"mp3", "wav", "aac", "flac", "pcm", "aiff", "ogg", "wma", "alac"});
        tagToExtensions.put("document", new String[] {"doc", "docx", "html", "htm", "odt", "pdf", "xls", "xlsx", "ods", "ppt", "pptx", "txt"});
        tagToExtensions.put("video", new String[] {"mp4", "mov", "wmv", "avi", "avchd", "mkv", "webm", "mkv"});
        tagToExtensions.put("image", new String[] {"jpg", "png", "gif", "tiff", "psd", "pdf", "eps", "ai", "indd", "raw"});

        return tagToExtensions;
    }
}
