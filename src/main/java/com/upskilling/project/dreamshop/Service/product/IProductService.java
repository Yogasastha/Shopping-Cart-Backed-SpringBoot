package com.upskilling.project.dreamshop.Service.product;

import com.upskilling.project.dreamshop.DTO.ProductDTO;
import com.upskilling.project.dreamshop.model.Product;
import com.upskilling.project.dreamshop.request.AddProductRequest;
import com.upskilling.project.dreamshop.request.UpdateProductRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    Product updateProductById(Long id, UpdateProductRequest request);
    void deleteProductById(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByNameAndBrand(String name, String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    Long countProductByNameAndBrand(String name, String brand);

    List<ProductDTO> getConvertedProducts(List<Product> products);


//    List<ProductDTO> getConvertedProducts(List<Product> products);
}
