package com.livecommerce.product.service;


import com.livecommerce.product.domain.Product;
import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    List<Product> searchProducts(String keyword);
    Product reduceStock(Long id, int quantity);
}