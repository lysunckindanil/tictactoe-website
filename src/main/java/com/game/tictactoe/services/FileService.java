package com.game.tictactoe.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;

@Service
public class FileService {
    @Value("${file.photos-dir}")
    private String dir;

    public void saveUserFile(MultipartFile file, String username) {
        Path saveTo = Paths.get(dir);
        try {
            if (!Files.exists(saveTo))
                Files.createDirectories(saveTo);
            Path targetLocation = saveTo.resolve(username + ".png");

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean userPhotoNotExists(String username) {
        return !Arrays.asList(Objects.requireNonNull(new File(dir).list())).contains(username + ".png");
    }
}
