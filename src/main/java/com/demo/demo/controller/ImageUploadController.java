package com.demo.demo.controller;

import com.demo.demo.model.Image;
import com.demo.demo.model.ResponseMessage;
import com.demo.demo.repository.ImageRepository;
import com.demo.demo.security.services.FileStorageService;
import com.demo.demo.security.services.ImageServiceImpl;
import com.demo.demo.security.services.iservice.ImageService;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@CrossOrigin(origins ="http://localhost:4200")
@RestController
@RequestMapping("/image")
public class ImageUploadController {

    @Autowired
    private ImageServiceImpl imageServiceImpl;
    @Autowired
    public void FilesController(ImageServiceImpl imageServiceImpl) {
        this.imageServiceImpl = imageServiceImpl;
    }

    @PostMapping(value = "/avatar",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) throws FileUploadException {
        imageServiceImpl.saveAvatar (file);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage ("Uploaded the file successfully: " + file.getOriginalFilename()));
    }

}
