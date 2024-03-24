package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;
    @GetMapping(path = "/all")
    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping(path = "/add")
    public String addNewCategory(@RequestParam String name) {
        if(name.trim().isEmpty() || name.isEmpty()) {
            return "Digite um nome válido";
        }

        Category category = new Category();
        category.setNome(name);

        categoryRepository.save(category);

        return "Success";
    }

    @PostMapping(path = "/update")
    public String updateAnCategory( @RequestParam Long id, @RequestParam String name) {

        if(name.trim().isEmpty())  return "Nome não informado";

        Optional<Category> category = categoryRepository.findById(id);

        if(category.isPresent()) {
            category.get().setNome(name);
            categoryRepository.save(category.get());
            return "Categoria atualizada.";
        } else {
            return "Categoria não encontrada.";
        }
    }

    @GetMapping(path ="/{id}")
    public Category getCategoryById(@PathVariable Long id) {

        if(id == null) {
            return null;
        }
        Optional<Category> category = categoryRepository.findById(id);

        return category.isPresent() ? category.get() : null;
    }

}
