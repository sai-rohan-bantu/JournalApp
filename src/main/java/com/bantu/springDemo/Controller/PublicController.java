package com.bantu.springDemo.Controller;

import com.bantu.springDemo.Entity.Users;
import com.bantu.springDemo.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    public UsersService usersService;

    @PostMapping("/addUser")
    public void addUser(@RequestBody Users user){
        usersService.saveNewUser(user);
    }

}
