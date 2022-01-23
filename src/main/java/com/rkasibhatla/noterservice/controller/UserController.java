package com.rkasibhatla.noterservice.controller;

import com.rkasibhatla.noterservice.dto.UserDto;
import com.rkasibhatla.noterservice.entity.User;
import com.rkasibhatla.noterservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        userService.registerUser(userDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto) {
        User foundUser = userService.findUserByUsername(userDto.getUsername());
        UserDto result = new UserDto();
        result.setFirstName(foundUser.getFirstName());
        result.setLastName(foundUser.getLastName());
        result.setUsername(foundUser.getUsername());
        return ResponseEntity.ok(result);
    }
}
