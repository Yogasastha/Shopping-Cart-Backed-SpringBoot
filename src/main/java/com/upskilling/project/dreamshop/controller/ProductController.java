package com.upskilling.project.dreamshop.controller;


import com.upskilling.project.dreamshop.Service.product.ProductService;
import com.upskilling.project.dreamshop.exception.ProductNotFoundException;
import com.upskilling.project.dreamshop.exception.ResourceNotFoundException;
import com.upskilling.project.dreamshop.model.Product;
import com.upskilling.project.dreamshop.request.AddProductRequest;
import com.upskilling.project.dreamshop.request.UpdateProductRequest;
import com.upskilling.project.dreamshop.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        return ResponseEntity.ok(new ApiResponse("Success", productList));
    }

    @GetMapping("/product/{id}/product")
    public  ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse("Success", product));
        }
        catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product theProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Successfully Added!", theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/product/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest request, @PathVariable Long id) {
        try {
            Product theProduct = productService.updateProductById(id, request);
            return ResponseEntity.ok(new ApiResponse("Product Updated Successfully!", theProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("product/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("Successfully Deleted!", id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Check the path Variable things if error
    @GetMapping("/product/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@PathVariable String brandName, @PathVariable String productName) {
        try {
            List<Product> productList = productService.getProductsByNameAndBrand(brandName, productName);
            if(productList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Product Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success!", productList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Check the path Variable things if error
    @GetMapping("/product/by/category-and-name")
    public ResponseEntity<ApiResponse> getProductByCategoryAndName(@PathVariable String category, @PathVariable String productName) {
        try {
            List<Product> productList = productService.getProductsByCategoryAndBrand(category, productName);
            if(productList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Product Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success!", productList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/{name}/product")
    public  ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
        try {
            List<Product> productList = productService.getProductsByName(name);
            if(productList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Product Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", productList));
        }
        catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
        }
    }

}
