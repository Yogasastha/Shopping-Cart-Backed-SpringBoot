package com.upskilling.project.dreamshop.repository;


import com.upskilling.project.dreamshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    static Category findByName(String name) {
        return null;
    }

    boolean isAlreadyExsist(String name);

    boolean existsByName(String name);
}
