package com.example.file.storage.controller;

import com.example.file.storage.document.File;
import com.example.file.storage.model.IdResponse;
import com.example.file.storage.service.abstraction.IFileService;
import org.springframework.web.bind.annotation.*;

@RestController
public class FileController {

    private final IFileService fileService;

    public FileController(IFileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/file")
    public IdResponse saveFile(@RequestBody File file) {
        return new IdResponse(fileService.save(file));
    }
}
