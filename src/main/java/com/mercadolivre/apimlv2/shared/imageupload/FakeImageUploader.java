package com.mercadolivre.apimlv2.shared.imageupload;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FakeImageUploader implements ImageUploader {

    @Override
    public Set<String> upload(List<MultipartFile> files) {
        return files.stream()
                    .map(this::getUrl)
                    .collect(Collectors.toSet());
    }

    private String getUrl(MultipartFile file) {
        String originalFileNameWithoutPoint = file.getOriginalFilename().split("\\.")[0];
        return MessageFormat.format("http://bucket-s3.com/{0}-{1}",originalFileNameWithoutPoint, UUID.randomUUID());
    }
}
