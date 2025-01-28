package com.nimap.curdOperation.service;

import com.nimap.curdOperation.Entity.Category;
import com.nimap.curdOperation.Entity.Product;
import com.nimap.curdOperation.dto.CategoryDTO;
import com.nimap.curdOperation.dto.DtoMapper;
import com.nimap.curdOperation.dto.ProductDTO;
import com.nimap.curdOperation.exception.IdNotFoundException;
import com.nimap.curdOperation.repository.CategoryRepository;
import com.nimap.curdOperation.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

     @Autowired
    private DtoMapper dtoMapper;

    public Page<CategoryDTO> getCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);  // Create a Pageable object
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(dtoMapper::convertToCategoryDTO);
    }


    public CategoryDTO createCategoryWithProducts(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());

        // Map the products from DTO to entity
        List<Product> products = categoryDTO.getProducts().stream().map(productDTO -> {
            Product product = new Product();
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setCategory(category); // Link product to category
            return product;
        }).collect(Collectors.toList());

        category.setProducts(products);

        Category savedCategory = categoryRepository.save(category);
        return dtoMapper.convertToCategoryDTO(savedCategory);
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return dtoMapper.convertToCategoryDTO(category);
    }
    public CategoryDTO updateCategoryWithProducts(Long id, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Category id is incorrect"));

        existingCategory.setName(categoryDTO.getName());

        // Clear existing products and update with new ones
        existingCategory.getProducts().clear();
        if (categoryDTO.getProducts() != null) {
            List<Product> updatedProducts = categoryDTO.getProducts().stream().map(productDTO -> {
                Product product = productRepository.findById(productDTO.getId())
                        .orElseThrow(() -> new IdNotFoundException("Product id is incorrect"));

                product.setName(productDTO.getName());
                product.setPrice(productDTO.getPrice());
                product.setCategory(existingCategory); // Link updated product to category
                return product;
            }).collect(Collectors.toList());

            existingCategory.getProducts().addAll(updatedProducts);
        }

        Category savedCategory = categoryRepository.save(existingCategory);
        return dtoMapper.convertToCategoryDTO(savedCategory);
    }
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        System.out.println("Category is deleted successfully.");
    }
}
