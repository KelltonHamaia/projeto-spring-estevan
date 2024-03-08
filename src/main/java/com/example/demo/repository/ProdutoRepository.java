package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.repository.CrudRepository;


public interface ProdutoRepository extends CrudRepository<Product, Long> {
    Product findProductById(Long id);

}
