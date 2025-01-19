package com.nimap.curdOperation.controller;

import com.nimap.curdOperation.Entity.Category;
import com.nimap.curdOperation.dto.CategoryDTO;
import com.nimap.curdOperation.dto.DtoMapper;
import com.nimap.curdOperation.repository.CategoryRepository;
import com.nimap.curdOperation.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private DtoMapper dtoMapper;

    @GetMapping
    public Page<CategoryDTO> getCategories(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "5") int size) {
        return categoryService.getCategories(page, size);
    }


    @PostMapping("/")
    public List<CategoryDTO> createCategories(@RequestBody List<CategoryDTO> categoryDTOs) {
        List<CategoryDTO> createdCategories = categoryDTOs.stream()
                .map(dto -> {
                    Category category = new Category();
                    category.setName(dto.getName());
                    // Call the service method
                    return categoryService.createCategory(category);
                })
                .collect(Collectors.toList());

        return createdCategories;
    }


    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public CategoryDTO updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(id);
        category.setName(categoryDTO.getName());

        return categoryService.updateCategory(id, category);
    }


    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        System.out.println("Category deleted sucessfully");
    }
}
