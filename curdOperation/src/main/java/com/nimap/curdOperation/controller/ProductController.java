package com.nimap.curdOperation.controller;

import com.nimap.curdOperation.Entity.Product;
import com.nimap.curdOperation.dto.ProductDTO;
import com.nimap.curdOperation.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public Page<ProductDTO> getProducts(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size) {
        return productService.getProducts(page, size);
    }

    @PostMapping("/")
    public List<ProductDTO> createProducts(@RequestBody List<ProductDTO> productDTOs) {
        List<ProductDTO> createdProducts = productDTOs.stream()
                .map(dto -> {
                    return productService.createProduct(dto);
                })
                .collect(Collectors.toList());
        return createdProducts;
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        return productService.updateProduct(id, product);
    }


    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        System.out.println("product deleted sucessfully");
        productService.deleteProduct(id);
    }
}
