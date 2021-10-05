package com.example.file.storage.controller;

import com.example.file.storage.document.File;
import com.example.file.storage.model.IdResponse;
import com.example.file.storage.model.Response;
import com.example.file.storage.model.ResponsePage;
import com.example.file.storage.service.abstraction.IFilePaginatorService;
import com.example.file.storage.service.abstraction.IFileService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FileController {

    private final IFileService fileService;
    private final IFilePaginatorService filePaginatorService;

    public FileController(IFileService fileService, IFilePaginatorService filePaginatorService) {
        this.fileService = fileService;
        this.filePaginatorService = filePaginatorService;
    }

    @PostMapping("/file")
    public ResponseEntity<?> saveFile(@RequestBody @Valid File file, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Response(false, "invalid data"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new IdResponse(fileService.save(file)), HttpStatus.OK);
    }

    @DeleteMapping("/file/{id}")
    public ResponseEntity<Response> deleteFile(@PathVariable String id) {
        if (fileService.delete(id)) {
            return new ResponseEntity<>(new Response(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response(false, "file not found"), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/file/{id}/tags")
    public ResponseEntity<Response> assignTags(@PathVariable String id, @RequestBody List<String> tags) {
        try {
            fileService.updateFileTags(id, tags);
            return new ResponseEntity<>(new Response(), HttpStatus.OK);
        } catch (Exception exception) {
            return null;
        }
    }

    @DeleteMapping("/file/{id}/tags")
    public ResponseEntity<Response> deleteTags(@PathVariable String id, @RequestBody List<String> tags) {
        try {
            fileService.deleteFileTags(id, tags);
        } catch (Exception exception) {
            return new ResponseEntity<>(new Response(false, "tag not found on file"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Response(), HttpStatus.OK);
    }

    @GetMapping("/file")
    public ResponsePage<File> getPageFiles(
        @RequestParam(value = "tags", required = false) List<String> tags,
        @RequestParam(value = "q", required = false, defaultValue = "") String name,
        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
        @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        if (tags == null || tags.isEmpty()) {
            return filePaginatorService.getByPage(name, pageable);
        }
        return filePaginatorService.getByPage(tags, name, pageable);
    }
}
