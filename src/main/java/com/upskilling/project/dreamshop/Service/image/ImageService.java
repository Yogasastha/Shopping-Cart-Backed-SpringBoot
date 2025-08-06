package com.upskilling.project.dreamshop.Service.image;

import com.upskilling.project.dreamshop.DTO.ImageDTO;
import com.upskilling.project.dreamshop.Service.product.IProductService;
import com.upskilling.project.dreamshop.exception.ResourceNotFoundException;
import com.upskilling.project.dreamshop.model.Image;
import com.upskilling.project.dreamshop.model.Product;
import com.upskilling.project.dreamshop.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ImageService implements IImageService{

    private ImageRepository imgRepo;
    private IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imgRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imgRepo.findById(id).ifPresentOrElse(imgRepo::delete, () -> {
            throw new ResourceNotFoundException("Image not found with id: " + id);
        });
    }

    @Override
    public List<ImageDTO> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDTO> savedImageDto = new ArrayList<>();

        for(MultipartFile file: files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imgRepo.save(image);

                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setImageId(savedImage.getId());
                imageDTO.setImageName(savedImage.getFileName());
                imageDTO.setDownloadUrl(savedImage.getDownloadUrl());

                savedImageDto.add(imageDTO);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImageById(MultipartFile file, Long imgId) {
       try {
           Image img = getImageById(imgId);
           img.setFileName(file.getOriginalFilename());
           img.setImage(new SerialBlob(file.getBytes()));
           imgRepo.save(img);
       } catch (IOException | SQLException e) {
           throw new RuntimeException(e.getMessage());
       }

    }
}
