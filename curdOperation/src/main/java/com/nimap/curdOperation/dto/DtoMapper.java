package com.nimap.curdOperation.dto;

import com.nimap.curdOperation.Entity.Category;
import com.nimap.curdOperation.Entity.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DtoMapper {
    // Convert Product entity to ProductDTO
    public ProductDTO convertToProductDTO(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getCategory().getId());
    }

    // Convert Category entity to CategoryDTO (now with products)
    public CategoryDTO convertToCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());

        // Convert each product to ProductDTO and add to the categoryDTO
        List<ProductDTO> productDTOs = category.getProducts().stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());

        categoryDTO.setProducts(productDTOs);
        return categoryDTO;
    }
}
