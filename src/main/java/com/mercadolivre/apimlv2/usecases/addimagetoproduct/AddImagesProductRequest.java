package com.mercadolivre.apimlv2.usecases.addimagetoproduct;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class AddImagesProductRequest {
    @NotNull
    @Size(min = 1)
    private List<MultipartFile> images;

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}
