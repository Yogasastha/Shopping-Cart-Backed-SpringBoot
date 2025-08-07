package com.upskilling.project.dreamshop.controller;

import com.upskilling.project.dreamshop.DTO.ImageDTO;
import com.upskilling.project.dreamshop.Service.image.IImageService;
import com.upskilling.project.dreamshop.model.Image;
import com.upskilling.project.dreamshop.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.Internal;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {

    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {
        try {
            List<ImageDTO> imageDTOS = imageService.saveImages(files, productId);
            return ResponseEntity.ok(new ApiResponse("Upload Successfull!", imageDTOS));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( new ApiResponse("Upload Failed", e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource  resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok(new ApiResponse("Downloaded Image", resource)).;
    }
}
