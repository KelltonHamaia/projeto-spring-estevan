package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;


@RestController
@RequestMapping(path = "/user")

public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping(path = "/all")
    public Iterable<User> getAll(){
        return usuarioRepository.findAll();
    }

    @PostMapping(path = "/new")
    public String addNewUser(
            @RequestParam  String nome,
            @RequestParam String email,
            @RequestParam  @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataNasc,
            @RequestParam String password)
    {
        User user = usuarioRepository.findByEmail(email);

        if(user != null) {
            return "E-mail já cadastrado.";
        }

        User newUser = new User();

        newUser.setNome(nome);
        newUser.setEmail(email);
        newUser.setDataNasc(dataNasc);
        newUser.setPassword(password);

        usuarioRepository.save(newUser);

        return "success";
    }

    @GetMapping("/userbyemail")
    public User getUserByEmail(@RequestParam String email)  {
        return usuarioRepository.findByEmail(email);
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String senha) {
        if(email == null || senha == null)  return "erro";

        User user = usuarioRepository.findByEmail(email);
        if(user == null)return "erro";

        if(user.getPassword().equals(senha)) {
            user.setLast_login(LocalDateTime.now());
            usuarioRepository.save(user);
            return "Ok";
        }
        return "erro";
    }

    @GetMapping("/datanascimento")
    public Iterable<User> getUserByDateRange(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date date_offset,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date date_limit
    ) {
        System.out.println(date_offset);
        System.out.println(date_limit);
        return usuarioRepository.findAllByDataNascBetween(date_offset, date_limit);
    }

    @GetMapping(path = "/{id}")

    public Optional<User> findById(@PathVariable Long id) {
        return usuarioRepository.findById(id);
    }

    @PostMapping(path = "/userupdate")
    public String updateDataNascimento(
            @RequestParam Long id,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date newDataNascimento
    ) {
        User user = usuarioRepository.findUserById(id);

        if(user != null) {
            user.setDataNasc(newDataNascimento);
            usuarioRepository.save(user);
            return "success";
        }
        return "error";
    }

    @GetMapping(path = "lastloggedusers")

    public ArrayList<User> gatLastLoggedUsers() {
        ArrayList<User> latestLoggedUsers = new ArrayList<User>();
        Iterable<User> users = usuarioRepository.findAll();

        users.forEach(user -> {
            if(user.getLast_login().getDayOfMonth() + 1 == LocalDateTime.now().getDayOfMonth()) {
                latestLoggedUsers.add(user);
            }
        });

        return latestLoggedUsers;
    }
}
