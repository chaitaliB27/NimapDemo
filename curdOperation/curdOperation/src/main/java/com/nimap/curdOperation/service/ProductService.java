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
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private DtoMapper dtoMapper;

    public Page<ProductDTO> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);  // Create a Pageable object
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(dtoMapper::convertToProductDTO);
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        // Find the Category by ID
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Create the product and set the category
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setCategory(category);

        // Save the product
        Product savedProduct = productRepository.save(product);

        // Return the saved product as DTO
        return new ProductDTO(savedProduct.getId(), savedProduct.getName(), savedProduct.getPrice(), category.getId());
    }



    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            // Map Product to ProductDTO with categoryId
            Long categoryId = product.getCategory().getId();
            return new ProductDTO(product.getId(), product.getName(), product.getPrice(), categoryId);
        }
        return null; // Return null or throw an exception if product not found
    }

    public ProductDTO updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Product id is incorrect"));

        existingProduct.setName(product.getName());

        existingProduct.setPrice(product.getPrice());
        Product updatedProduct = productRepository.save(existingProduct);

        return dtoMapper.convertToProductDTO(updatedProduct);
    }


    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
        System.out.println("product is deleted successfully.");
    }
}
