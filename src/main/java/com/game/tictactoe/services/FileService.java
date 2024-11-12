package com.game.tictactoe.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    @Value("${file.upload-dir}")
    private String dir;

    public void saveUserFile(MultipartFile file, String username) {
        Path saveTo = Paths.get(dir, "profile_photos");
        try {
            if (!Files.exists(saveTo))
                Files.createDirectories(saveTo);
            Path targetLocation = saveTo.resolve(username + ".png");

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
