package com.lms.api.service;

import com.lms.api.entity.Product;
import com.lms.api.repo.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService service;
    @Mock
    private ProductRepository repo;

    private Product product;

    @BeforeEach
    public void setUp() {
        product = Product.builder()
                .id("testId")
                .name("testName")
                .price(100)
                .build();
    }

    @Test
    void testCreateProduct() {
        service.createProduct(new Product());
        Mockito.verify(repo, Mockito.times(1)).save(Mockito.any(Product.class));
    }

    @Test
    void testGetAllProducts() {
        service.getAllProducts();
        Mockito.verify(repo, Mockito.times(1)).findAll();
    }

    @Test
    void testGetProductById_ProductNotFound() {
        Mockito.when(repo.findById(Mockito.anyString())).thenReturn(Optional.of(new Product()));
        ResponseEntity<Product> product = service.getProductById("testId");
        Mockito.verify(repo, Mockito.times(1)).findById("testId");
        Assertions.assertNotNull(product);
    }

    @Test
    void testGetProductById() {
        Mockito.when(repo.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        ResponseEntity<Product> product = service.getProductById("testId");
        Mockito.verify(repo, Mockito.times(1)).findById("testId");
        Assertions.assertNotNull(product);
        assertNotNull(product.getBody());
        Assertions.assertEquals("testId",product.getBody().getId());
    }

    @Test
    void updateProduct_WhenProductExists() {
        Mockito.when(repo.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        service.updateProduct("testId", product);
        Mockito.verify(repo, Mockito.times(1)).findById("testId");
        Mockito.verify(repo, Mockito.times(1)).save(Mockito.any(Product.class));
    }

    @Test
    void updateProduct_WhenProductNotExists() {
        Mockito.when(repo.findById(Mockito.anyString())).thenReturn(Optional.of(new Product()));
        service.updateProduct("testId", product);
        Mockito.verify(repo, Mockito.times(1)).findById("testId");
        Mockito.verify(repo, Mockito.times(1)).save(Mockito.any(Product.class));
    }

    @Test
    void deleteProduct() {
        Mockito.when(repo.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        service.deleteProduct("testId");
        Mockito.verify(repo, Mockito.times(1)).delete(product);
    }
}