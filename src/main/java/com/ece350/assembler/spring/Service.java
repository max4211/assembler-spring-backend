package com.ece350.assembler.spring;

import com.ece350.assembler.bucket.BucketName;
import com.ece350.assembler.filestore.FileStore;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@org.springframework.stereotype.Service
public class Service {

    private final FileStore fileStore;

    @Autowired
    public Service(FileStore fileStore) {
        this.fileStore = fileStore;
    }

    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        // 1. Check if image is not empty
        isEmpty(file);

        // 2. If file is an image
        isImage(file);

        // 4. Grab some metadata from file if any
        Map<String, String> metadata = extractMetadata(file);

        // 5. Store the image in s3 and update database with s3 image link
        String path = String.format("%s", BucketName.PROFILE_IMAGE.getBucketName());
        String fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
        try {
            fileStore.save(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @org.jetbrains.annotations.NotNull
    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private void isEmpty(MultipartFile file) {
        if (file.isEmpty())
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
    }

    private void isImage(MultipartFile file) {
        if (!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(), ContentType.IMAGE_PNG.getMimeType(), ContentType.IMAGE_GIF.getMimeType()).contains(file.getContentType()))
            throw new IllegalStateException(String.format("File must be an image, but of type: %s", file.getContentType()));
    }

}
