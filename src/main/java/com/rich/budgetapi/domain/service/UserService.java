package com.rich.budgetapi.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rich.budgetapi.domain.exception.DomainException;
import com.rich.budgetapi.domain.exception.UserNotFoundException;
import com.rich.budgetapi.domain.model.User;
import com.rich.budgetapi.domain.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User toSave(User newUser) {
        Optional<User> userExists = userRepository.findByEmail(newUser.getEmail());

        if (userExists.isPresent() && !userExists.get().equals(newUser)) {
            throw new DomainException(
                    String.format("Já existe um usuário com o e-mail %s cadastrado", newUser.getEmail()));
        }

        return userRepository.save(newUser);
    }

    @Transactional
    public void toRemove(Long userId) {
        User user = findOrFail(userId);

        userRepository.delete(user);
    }

    @Transactional
    public void toChangePassword(Long userId, String passwordCurrent, String newPassword) {
        User user = findOrFail(userId);

        if (!passwordCurrent.equals(user.getPassword())) {
            throw new DomainException("Senha atual incorreta. Insira a senha atual correta e tente novamente.");
        }

        user.setPassword(newPassword);
    }

    public User findOrFail(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
