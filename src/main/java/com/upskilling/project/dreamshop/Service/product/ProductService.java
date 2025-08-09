package com.upskilling.project.dreamshop.Service.product;

import com.upskilling.project.dreamshop.exception.ResourceNotFoundException;
import com.upskilling.project.dreamshop.model.Category;
import com.upskilling.project.dreamshop.model.Product;
import com.upskilling.project.dreamshop.repository.CategoryRepository;
import com.upskilling.project.dreamshop.repository.productRepo;
import com.upskilling.project.dreamshop.request.AddProductRequest;
import com.upskilling.project.dreamshop.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final productRepo repo;

    private CategoryRepository categoryRepository;
    @Override
    public Product addProduct(AddProductRequest request) {
        Category category = Optional.ofNullable(CategoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                }
                );
        return createProduct(request, category);
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
        return repo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not Found!"));
    }

    @Override
    public Product updateProductById(Long id, UpdateProductRequest request) {
        return repo.findById(id)
                .map(existing -> updateExistingProduct(existing, request))
                .map(repo::save)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"))
                ;
    }

    public Product updateExistingProduct(Product existing, UpdateProductRequest update) {
        existing.setBrand(update.getBrand());
        existing.setName(update.getName());
        existing.setPrice(update.getPrice());
        existing.setDescription(update.getDescription());
        existing.setInventory(update.getInventory());
        Category category = CategoryRepository.findByName(update.getCategory().getName());
        existing.setCategory(category);
        return existing;
    }

    @Override
    public void deleteProductById(Long id) {
        repo.findById(id).ifPresentOrElse(Product -> repo.deleteById(id),
                () -> {throw new ResourceNotFoundException("Product not Found!");});
//      repo.findById(id).ifPresentOrElse(repo::delete,
//              () -> {throw new ResourceNotFoundException("Product not Found!");});
    }

    @Override
    public List<Product> getAllProducts() {
        return repo.findAll();
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
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepo.findByCategoryAndBrand(category, brand);
    }

    @Override
    public Long countProductByNameAndBrand(String brand, String name) {
        return productRepo.countByNameAndBrand(name, brand);
    }
}
