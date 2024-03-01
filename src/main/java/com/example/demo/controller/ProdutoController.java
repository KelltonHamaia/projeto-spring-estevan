package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/products")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Product> getAll(){
        return produtoRepository.findAll();
    }

    @PostMapping(path = "/new")
    public @ResponseBody String addNewUser(@RequestParam String nome, @RequestParam String desc, @RequestParam int estoque) {
        Product product = new Product();
        product.setNome(nome);
        product.setDescricao(desc);
        product.setEstoque(estoque);

        produtoRepository.save(product);

        return "success";
    }
}
