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


    public CategoryDTO createCategory(Category category) {
        Category savedCategory = categoryRepository.save(category);
        return dtoMapper.convertToCategoryDTO(savedCategory);
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return dtoMapper.convertToCategoryDTO(category);
    }


    public CategoryDTO updateCategory(Long id, Category category) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Category id is incorrect"));

        existingCategory.setName(category.getName());
        categoryRepository.save(existingCategory);

        CategoryDTO updatedCategoryDTO = new CategoryDTO();
        updatedCategoryDTO.setId(existingCategory.getId());
        updatedCategoryDTO.setName(existingCategory.getName());


        return updatedCategoryDTO;
    }


    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        System.out.println("Category is deleted successfully.");
    }
}
