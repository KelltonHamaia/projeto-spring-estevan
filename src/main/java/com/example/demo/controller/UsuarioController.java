package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path = "/user")

public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAll(){
        return usuarioRepository.findAll();
    }

    @PostMapping(path = "/new")
    public @ResponseBody String addNewUser(@RequestParam  String nome, @RequestParam String email) {
        User user = new User();

        user.setNome(nome);
        user.setEmail(email);

        usuarioRepository.save(user);

        return "success";
    }
}
