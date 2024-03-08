package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping(path = "/products")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping(path = "/all")
    public Iterable<Product> getAll(){
        return produtoRepository.findAll();
    }

    @PostMapping(path = "/new")
    public String addNewUser(@RequestParam String nome, @RequestParam String desc, @RequestParam int estoque) {
        Product product = new Product();
        product.setNome(nome);
        product.setDescricao(desc);
        product.setEstoque(estoque);

        produtoRepository.save(product);

        return "success";
    }

    @PostMapping(path = "/update")
    public String updateProduct(@RequestParam Long id, @RequestParam int novoEstoque) {
        Product product = produtoRepository.findProductById(id);

        if(product == null) {
            return "produto não cadastrado.";
        }
        product.setEstoque(novoEstoque);

        produtoRepository.save(product);
        return "success";
    }

    @GetMapping(path = "/lastupdated")
    public ArrayList<Product> getLastUpdatedProducts(){
        LocalDateTime today = LocalDateTime.now();
        ArrayList<Product> lastUpdatedProducts = new ArrayList<Product>();

        Iterable<Product> products = produtoRepository.findAll();

        products.forEach(product ->  {
            if(product.getUpdated_at().getDayOfMonth() + 1 == today.getDayOfMonth())  {
                lastUpdatedProducts.add(product);
            }
        });

        return lastUpdatedProducts;
    }

}
