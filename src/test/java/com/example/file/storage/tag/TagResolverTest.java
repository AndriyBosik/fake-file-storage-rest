package com.example.file.storage.tag;

import com.example.file.storage.tag.implementation.TagResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TagResolverTest {

    private static TagResolver tagResolver;

    @BeforeAll
    public static void beforeAll() {
        Map<String, String[]> tagToExtensions = new HashMap<>();

        tagToExtensions.put("audio", new String[] {"mp3", "wav", "aac", "flac", "pcm", "aiff", "ogg", "wma", "alac"});
        tagToExtensions.put("document", new String[] {"doc", "docx", "html", "htm", "odt", "pdf", "xls", "xlsx", "ods", "ppt", "pptx", "txt"});
        tagToExtensions.put("video", new String[] {"mp4", "mov", "wmv", "avi", "avchd", "mkv", "webm", "mkv"});
        tagToExtensions.put("image", new String[] {"jpg", "png", "gif", "tiff", "psd", "pdf", "eps", "ai", "indd", "raw"});

        tagResolver = new TagResolver(tagToExtensions);
    }

    @Test
    public void shouldReturnCorrectFileTag() {
        String tag = tagResolver.getTagByFilename("music.mp3");

        assertEquals("audio", tag);
    }

    @Test
    public void shouldReturnNullForNonExistingExtension() {
        String tag = tagResolver.getTagByFilename("tag.ext");

        assertNull(tag);
    }

    @Test
    public void shouldReturnNullForEmptyFilename() {
        String tag = tagResolver.getTagByFilename("");

        assertNull(tag);
    }
}
