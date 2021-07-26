package com.demo.demo.security.services.iservice;

import com.demo.demo.model.Image;
import com.demo.demo.model.Users;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public interface ImageService {
    public void saveImage(Image image);
    Optional<Image> findByName(String name);
    public  void saveUser(Users users);

    public void init();

    public void save(MultipartFile file);

    public Resource load(String filename);

    public void deleteAll();

    public Stream<Path> loadAll();
}
