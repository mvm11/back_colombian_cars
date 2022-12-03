package com.id.colombiancars.controller;


import com.id.colombiancars.entity.User;
import com.id.colombiancars.request.UserRequest;
import com.id.colombiancars.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {


    @Autowired
    private UserService userService;

    // Find by id
    @GetMapping(value = "/findById/{userId}")
    public ResponseEntity<User> findUser (@PathVariable Long userId) {
        return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);

    }


    // Find All
    @GetMapping(value = "/findAll")
    public List<User> findAllUsers(){
        return userService.findAllUsers();
    }


    // Save
    @PostMapping(value = "/save")
    public ResponseEntity<User> saveUser (@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.saveUser(userRequest), HttpStatus.CREATED);

    }

    // Update
    @PutMapping(value = "/update/{userId}")
    public ResponseEntity<User> updateUser (@PathVariable Long userId, @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.updateUser(userId, userRequest), HttpStatus.OK);

    }

    // Delete
    @DeleteMapping(value = "/delete/{userId}")
    public ResponseEntity<String> deleteUser (@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("The user has been deleted successful", HttpStatus.OK);

    }


}
