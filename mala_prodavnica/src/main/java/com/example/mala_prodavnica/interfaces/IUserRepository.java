package com.example.mala_prodavnica.interfaces;

import com.example.mala_prodavnica.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    Optional<User> findById(int id);
    Optional<User> login(String username,String password);
    List<User> findAll();
    User update(User user);

    Optional<User> getUserByUsername(String username);

    void register(User u);
}
