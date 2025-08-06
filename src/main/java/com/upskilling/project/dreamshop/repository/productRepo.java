package com.upskilling.project.dreamshop.repository;

import com.upskilling.project.dreamshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface productRepo extends JpaRepository<Product, Long> {

    static List<Product> findByCategoryName(String category) {
    }

    static List<Product> findByBrand(String brand) {
    }

    static List<Product> findByCategoryAndBrand(String category, String brand) {
    }

    static List<Product> findByName(String name) {
    }

    static List<Product> findByNameAndBrand(String name, String brand) {
    }

    static Long countByNameAndBrand(String name, String brand) {
        return null;
    }
}
