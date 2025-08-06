package com.upskilling.project.dreamshop.Service.image;

import com.upskilling.project.dreamshop.DTO.ImageDTO;
import com.upskilling.project.dreamshop.model.Image;
import com.upskilling.project.dreamshop.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDTO> saveImages(List <MultipartFile> files, Long productId);
    void updateImageById(MultipartFile file, Long imgId);
}
