package com.upskilling.project.dreamshop.repository;

import com.upskilling.project.dreamshop.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
