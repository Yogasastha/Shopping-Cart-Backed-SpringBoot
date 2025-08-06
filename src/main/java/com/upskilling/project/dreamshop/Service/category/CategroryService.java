package com.upskilling.project.dreamshop.Service.category;
import com.upskilling.project.dreamshop.exception.AlreadyExistsException;
import com.upskilling.project.dreamshop.exception.ResourceNotFoundException;
import com.upskilling.project.dreamshop.model.Category;
import com.upskilling.project.dreamshop.repository.CategoryRepository;
import com.upskilling.project.dreamshop.request.AddCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategroryService implements ICategroryService{
    private final CategoryRepository categoryRepository;

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete,() -> {
            throw new ResourceNotFoundException("Category Not Found");
        });
    }

    @Override
    public Category updateCategoryById(Long id, AddCategory request) {
        return Optional.ofNullable(getCategoryById(id))
                .map(oldCategory -> {
                    oldCategory.setName(request.getName());
                    return categoryRepository.save(oldCategory);
                }).orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));
    }


    @Override
    public Category addCategory(AddCategory request) {

        return Optional.of(request).filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(new AlreadyExistsException(request.getName() +" is already exsist!!"));
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            throw new com.upskilling.project.dreamshop.exception.ResourceNotFoundException("Category Not Found");
        });
    }

    @Override
    public Category getCategoryByName(String name) {
        return CategoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
