package com.lms.api.service;

import com.lms.api.entity.Product;
import com.lms.api.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repo;

    public Product createProduct(@RequestBody Product product) {
        return repo.save(product);
    }

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public ResponseEntity<Product> getProductById(String id) {
        Optional<Product> product = repo.findById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Product> updateProduct(String id, Product productDetails) {
        return repo.findById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setPrice(productDetails.getPrice());
            Product updated = repo.save(product);
            return ResponseEntity.ok(updated);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> deleteProduct(String id) {
        return repo.findById(id).map(product -> {
            repo.delete(product);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

