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
    public CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.createCategoryWithProducts(categoryDTO);
    }


    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public CategoryDTO updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        return categoryService.updateCategoryWithProducts(id, categoryDTO);
    }


    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        System.out.println("Category deleted sucessfully");
    }
}
