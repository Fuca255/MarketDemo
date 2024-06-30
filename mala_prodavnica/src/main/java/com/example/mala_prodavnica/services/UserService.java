package com.example.mala_prodavnica.services;

import com.example.mala_prodavnica.beans.UserBean;
import com.example.mala_prodavnica.interfaces.IUserRepository;
import com.example.mala_prodavnica.model.User;
import com.example.mala_prodavnica.repository.UserRepository;

import java.util.Optional;

public class UserService {

    private final IUserRepository userRepository;

    public UserService()
    {
        userRepository = new UserRepository();
    }

    public Optional<UserBean> login(String username,String password)
    {
        Optional<User> user = userRepository.login(username,password);
        if(user.isEmpty())
            return Optional.empty();
        User u = user.get();
        UserBean loggedUser = new UserBean(u.getId(),u.getUsername(),u.getMoney(),u.isAdmin());
        return Optional.of(loggedUser);
    }

    public boolean register(String username, String password)
    {
        Optional<User> ex_user = userRepository.getUserByUsername(username);
        if(ex_user.isPresent())
            return false;

        User user =  new User();
        user.setAdmin(false);
        user.setMoney(100000000);
        user.setPassword(password);
        user.setUsername(username);
        userRepository.register(user);

        return true;
    }
    public void updateUserMoney(UserBean user)
    {
        Optional<User> u = userRepository.findById(user.getId());
        if(u.isPresent())
        {
            User updated = u.get();
            updated.setMoney(user.getMoney());
            userRepository.update(updated);
        }
    }

}
