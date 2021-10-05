package com.example.file.storage.service;

import com.example.file.storage.document.File;
import com.example.file.storage.repository.abstraction.IExtendedFileRepository;
import com.example.file.storage.repository.abstraction.IFileRepository;
import com.example.file.storage.service.abstraction.IFileService;
import com.example.file.storage.service.implementation.FileService;
import com.example.file.storage.tag.abstraction.ITagResolver;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

public class FileServiceTest {

    @Test
    public void shouldSaveWithResolvedTag() {
        List<File> files = new ArrayList<>();
        final String fileId = "file-id";

        IFileRepository fileRepository = Mockito.mock(IFileRepository.class);
        ITagResolver tagResolver = Mockito.mock(ITagResolver.class);
        Mockito.when(tagResolver.getTagByFilename(any())).thenReturn("audio");
        Mockito.doAnswer(invocationOnMock -> {
            files.add(invocationOnMock.getArgument(0));
            ((File) invocationOnMock.getArgument(0)).setId(fileId);
            return invocationOnMock.getArgument(0);
        }).when(fileRepository).save(any(File.class));

        File file = new File();
        file.setName("filename.mp3");
        file.setSize(123);

        FileService fileService = new FileService(fileRepository, null, tagResolver);
        String id = fileService.save(file);

        assertEquals(fileId, id);
        assertTrue(file.getTags().contains("audio"));
        assertEquals(1, files.size());
    }

    @Test
    public void shouldDeleteFile() {
        List<File> files = new ArrayList<>();
        files.add(new File("file-id", "file-name", 123, new ArrayList<>()));

        IExtendedFileRepository extendedFileRepository = Mockito.mock(IExtendedFileRepository.class);
        Mockito.doAnswer(invocationOnMock -> {
            files.removeIf(file -> file.getId().equals(invocationOnMock.getArgument(0)));
            return null;
        }).when(extendedFileRepository).deleteFile(any());

        FileService fileService = new FileService(null, extendedFileRepository, null);
        fileService.delete("file-id");

        assertEquals(0, files.size());
    }

    @Test
    public void shouldUpdateFileTags() throws Exception {
        IExtendedFileRepository extendedFileRepository = Mockito.mock(IExtendedFileRepository.class);
        final File file = new File("", "filename", 10000, new ArrayList<>());
        Mockito.doAnswer(invocationOnMock -> {
            file.setTags(invocationOnMock.getArgument(1));
            return null;
        }).when(extendedFileRepository).updateFileTags(any(), any());

        FileService fileService = new FileService(null, extendedFileRepository, null);
        List<String> tags = new ArrayList<>();
        tags.add("tag 1");
        tags.add("new");
        tags.add("tag");
        fileService.updateFileTags("", tags);

        assertEquals(tags.size(), file.getTags().size());
        for (int i = 0; i < tags.size(); i++) {
            assertEquals(tags.get(i), file.getTags().get(i));
        }
    }

    @Test
    public void shouldDeleteFileTags() throws Exception {
        List<String> tags = new ArrayList<>();
        tags.add("tag 1");
        tags.add("new");
        tags.add("tag");

        IExtendedFileRepository extendedFileRepository = Mockito.mock(IExtendedFileRepository.class);
        final File file = new File("", "filename", 10000, tags);
        Mockito.doAnswer(invocationOnMock -> {
            file.getTags().removeIf(tag -> ((List<String>) invocationOnMock.getArgument(1)).contains(tag));
            return null;
        }).when(extendedFileRepository).deleteFileTags(any(), any());

        FileService fileService = new FileService(null, extendedFileRepository, null);
        List<String> tagsToDelete = new ArrayList<>();
        tagsToDelete.add("new");
        tagsToDelete.add("tag");
        fileService.deleteFileTags("", tagsToDelete);

        assertEquals(1, file.getTags().size());
        assertEquals("tag 1", file.getTags().get(0));
    }
}
