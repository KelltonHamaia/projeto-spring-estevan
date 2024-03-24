package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(path = "/products")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(path = "/all")
    public Iterable<Product> getAll(){
        return produtoRepository.findAll();
    }

    @PostMapping(path = "/new")
    public String addNewProduct(
            @RequestParam String nome,
            @RequestParam String desc,
            @RequestParam int estoque,
            @RequestParam Long idCategory
    ) {
        Product product = new Product();
        Optional<Category> category = categoryRepository.findById(idCategory);

        if(category.isPresent()) {
            product.setCategory(category.get());
            product.setNome(nome);
            product.setDescricao(desc);
            product.setEstoque(estoque);
            produtoRepository.save(product);
            return "success";
        } else {
            return "erro - categoria não encontrada.";
        }
    }

    @PostMapping(path = "/update")
    public String updateProduct(@RequestParam Long id, @RequestParam int novoEstoque) {
        Optional<Product> product = produtoRepository.findById(id);

        if (product.isPresent()) {
            if(novoEstoque < 0) {
                return "Informe um valor maior que zero.";
            }
            product.get().setEstoque(novoEstoque);
            produtoRepository.save(product.get());
            return "success";
        } else  {
            return "Produto não existe.";
        }

    }

    @GetMapping(path = "/lastupdated")
    public Iterable<Product> getLastUpdatedProducts(){
        Date yesterday = new Date(System.currentTimeMillis() - (1000 * 3600 * 24));
        return produtoRepository.findByUpdatedAtAfter(yesterday);
    }

}
