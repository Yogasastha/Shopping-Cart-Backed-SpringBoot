package com.upskilling.project.dreamshop.controller;


import com.upskilling.project.dreamshop.Service.category.ICategroryService;
import com.upskilling.project.dreamshop.exception.AlreadyExistsException;
import com.upskilling.project.dreamshop.exception.ResourceNotFoundException;
import com.upskilling.project.dreamshop.model.Category;
import com.upskilling.project.dreamshop.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategroryService categroryService;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategeory(@RequestBody  Category name) {
        try {
         Category category = categroryService.addCategory(name);
         return ResponseEntity.ok(new ApiResponse("Success", category));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        try {
            Category category = categroryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found", category));
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable String  name) {
        try {
            Category category = categroryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found", category));
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        try {
            categroryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Deleted Successfully", null));
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            Category updatedCategory = categroryService.updateCategoryById(id, category);
            return ResponseEntity.ok(new ApiResponse("Updated Successfully!", updatedCategory));
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categories = categroryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found", categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

}
