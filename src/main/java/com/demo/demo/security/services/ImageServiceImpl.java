package com.demo.demo.security.services;

import com.demo.demo.model.Image;
import com.demo.demo.model.Users;
import com.demo.demo.repository.ImageRepository;
import com.demo.demo.repository.UserRepository;
import com.demo.demo.security.services.iservice.ImageService;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveImage(Image image) {
        imageRepository.save (image);
    }

    @Override
    public Optional<Image> findByName(String name) {
        return imageRepository.findByName (name);
    }

    @Override
    public void saveUser(Users users){
        userRepository.save (users);
    }
//????


    private final Path root = Paths.get("uploads");
    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource (file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    @Value("${upload.path}")
    private String uploadPath;
    public void saveAvatar(MultipartFile file) throws FileUploadException {
        try {
            Path root = Paths.get(uploadPath);
            Path resolve = root.resolve(file.getOriginalFilename());
            if (resolve.toFile()
                    .exists()) {
                throw new FileUploadException("File already exists: " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), resolve);
        } catch (Exception e) {
            throw new FileUploadException("Could not store the file. Error: " + e.getMessage());
        }
    }
}
