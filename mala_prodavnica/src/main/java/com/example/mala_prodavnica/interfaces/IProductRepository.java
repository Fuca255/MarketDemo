package com.example.mala_prodavnica.interfaces;

import com.example.mala_prodavnica.beans.UpdateProductBean;
import com.example.mala_prodavnica.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductRepository {
    Optional<Product> getProductById(int id);
    List<Product> getAllProducts();
    Product updateProduct(UpdateProductBean pr);
    void deleteProduct(Product p);
    Product addNewProduct(Product p);
}
