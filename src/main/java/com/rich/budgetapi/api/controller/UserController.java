package com.rich.budgetapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rich.budgetapi.api.assembler.userAssembler.UserInputModelDisassembler;
import com.rich.budgetapi.api.assembler.userAssembler.UserModelAssembler;
import com.rich.budgetapi.api.model.UserModel;
import com.rich.budgetapi.api.model.input.UserInputModel;
import com.rich.budgetapi.api.utils.ResourceUriHelper;
import com.rich.budgetapi.domain.model.User;
import com.rich.budgetapi.domain.repository.UserRepository;
import com.rich.budgetapi.domain.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserModelAssembler userAssembler;

    @Autowired
    private UserInputModelDisassembler userInputModelDisassembler;

    @GetMapping
    public ResponseEntity<List<UserModel>> toList() {
        List<UserModel> users = userAssembler.toCollectionModel(userRepository.findAll());

        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserModel> toFind(@PathVariable Long userId) {
        return ResponseEntity.ok().body(userAssembler.toModel(userService.findOrFail(userId)));
    }

    @PostMapping
    public ResponseEntity<UserModel> toSave(@RequestBody @Valid UserInputModel userInput) {
        User newUser = userInputModelDisassembler.toDomainObject(userInput);

        newUser = userService.toSave(newUser);

        return ResponseEntity
                .created(ResourceUriHelper.addUriInResponseHeader(newUser.getId()))
                .body(userAssembler.toModel(newUser));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserModel> toUpdate(@PathVariable Long userId, @RequestBody @Valid UserInputModel userInput) {
        User userCurrent = userService.findOrFail(userId);

        userInputModelDisassembler.copyToDomainObject(userInput, userCurrent);

        userCurrent = userService.toSave(userCurrent);

        return ResponseEntity.ok().body(userAssembler.toModel(userCurrent));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> toRemove(@PathVariable Long userId) {
        userService.toRemove(userId);

        return ResponseEntity.noContent().build();
    }
}