package com.mercadolivre.apimlv2.shared.imageupload;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ImageUploader {
    /**
     * Upload a image to a bucket and return its url.
     * @param files Images to be uploaded
     * @return Set containing urls of uploaded files
     */
    Set<String> upload(List<MultipartFile> files);
}
