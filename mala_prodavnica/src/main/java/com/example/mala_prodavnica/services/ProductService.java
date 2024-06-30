package com.example.mala_prodavnica.services;

import com.example.mala_prodavnica.beans.ProductBean;
import com.example.mala_prodavnica.beans.UpdateProductBean;
import com.example.mala_prodavnica.model.Product;
import com.example.mala_prodavnica.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductService {

    private ProductRepository repository;

    public ProductService()
    {
        repository = new ProductRepository();
    }

    public List<ProductBean> getAllProducts()
    {
        List<Product> products = repository.getAllProducts();
        List<ProductBean> beans = new ArrayList<>();
        for (Product p : products)
        {
            beans.add(toProductBean(p));
        }
        return beans;
    }

    public ProductBean createProduct(String name, int price)
    {
        Product p = new Product();
        p.setName(name);
        p.setPrice(price);
        return toProductBean(repository.addNewProduct(p));
    }

    public ProductBean updateProduct(UpdateProductBean p)
    {
        return toProductBean(repository.updateProduct(p));
    }

    public void deleteProduct(int product_id)
    {
        Optional<Product> p = repository.getProductById(product_id);
        if(p.isPresent())
        {
            repository.deleteProduct(p.get());
        }
    }
    private ProductBean toProductBean(Product p)
    {
        ProductBean bean = new ProductBean();
        bean.setId(p.getId());
        bean.setName(p.getName());
        bean.setPrice(p.getPrice());
        return bean;
    }
}
