package com.rich.budgetapi.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rich.budgetapi.domain.exception.UserNotFoundException;
import com.rich.budgetapi.domain.model.User;
import com.rich.budgetapi.domain.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User toSave(User newUser) {
        return userRepository.save(newUser);
    }

    @Transactional
    public void toRemove(Long userId) {
        User user = findOrFail(userId);

        userRepository.delete(user);
    }

    public User findOrFail(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
