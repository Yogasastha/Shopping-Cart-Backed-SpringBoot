package com.upskilling.project.dreamshop.Service.category;

import com.upskilling.project.dreamshop.model.Category;
import com.upskilling.project.dreamshop.request.AddCategory;

import java.util.List;

public interface ICategroryService {
    void deleteCategoryById(Long id);
    Category updateCategoryById(Long id, AddCategory request);
    Category addCategory(AddCategory request);
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
}
