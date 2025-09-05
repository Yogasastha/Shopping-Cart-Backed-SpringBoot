package com.upskilling.project.dreamshop.Service.product;

import com.upskilling.project.dreamshop.exception.ResourceNotFoundException;
import com.upskilling.project.dreamshop.model.Category;
import com.upskilling.project.dreamshop.model.Product;
import com.upskilling.project.dreamshop.repository.CategoryRepository;
import com.upskilling.project.dreamshop.repository.productRepo;
import com.upskilling.project.dreamshop.request.AddProductRequest;
import com.upskilling.project.dreamshop.request.UpdateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    private final productRepo productRepo;
    private final CategoryRepository categoryRepository;

//    @Autowired  // Optional if only one constructor, but good for clarity
    public ProductService(productRepo productRepo, CategoryRepository categoryRepository) {
        this.productRepo = productRepo;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product addProduct(AddProductRequest request) {
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        Product product = productRepo.save(createProduct(request, category));
        return product;
    }

    public Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getDescription(),
                request.getPrice(),
                request.getInventory(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not Found!"));
    }

    @Override
    public Product updateProductById(Long id, UpdateProductRequest request) {
        return productRepo.findById(id)
                .map(existing -> updateExistingProduct(existing, request))
                .map(productRepo::save)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
    }

    public Product updateExistingProduct(Product existing, UpdateProductRequest update) {
        existing.setBrand(update.getBrand());
        existing.setName(update.getName());
        existing.setPrice(update.getPrice());
        existing.setDescription(update.getDescription());
        existing.setInventory(update.getInventory());
        Category category = categoryRepository.findByName(update.getCategory().getName());
        existing.setCategory(category);
        return existing;
    }

    @Override
    public void deleteProductById(Long id) {
        productRepo.findById(id).ifPresentOrElse(
                product -> productRepo.deleteById(id),
                () -> { throw new ResourceNotFoundException("Product not Found!"); }
        );
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepo.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepo.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepo.findByName(name);
    }

    @Override
    public List<Product> getProductsByNameAndBrand(String name, String brand) {
        return productRepo.findByNameAndBrand(name, brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String categoryName, String brand) {
        return productRepo.findByCategoryNameAndBrand(categoryName, brand);
    }

    @Override
    public Long countProductByNameAndBrand(String brand, String name) {
        return productRepo.countByNameAndBrand(name, brand);

    }
}
