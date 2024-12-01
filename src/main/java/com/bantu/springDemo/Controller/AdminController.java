package com.bantu.springDemo.Controller;

import com.bantu.springDemo.Entity.Users;
import com.bantu.springDemo.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    public UsersService usersService;

    @GetMapping("/get-users")
    public ResponseEntity<?> getAllUsers(){
        List<Users> allUsers=usersService.getUsers();
        if(!allUsers.isEmpty() && allUsers!=null){
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/create-admin-user")
    public void createAdminUser(@RequestBody Users user){
        usersService.saveAdmin(user);
    }
}
