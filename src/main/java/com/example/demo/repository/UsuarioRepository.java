package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);

    Iterable<User> findAllByDataNascBetween(Date data_offset, Date data_limit);

   Optional<User> findById(Long id);

   User findUserById(Long id);

}
