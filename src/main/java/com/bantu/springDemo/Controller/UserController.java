package com.bantu.springDemo.Controller;

import com.bantu.springDemo.ApiResponse.WeatherResponse;
import com.bantu.springDemo.Entity.Users;
import com.bantu.springDemo.Repository.UsersRepo;
import com.bantu.springDemo.Services.UsersService;
import com.bantu.springDemo.Services.WeatherService;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UsersService usersService;

    @Autowired
    public UsersRepo usersRepo;

    @Autowired
    public WeatherService weatherService;


    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody Users user){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        Users oldUser=usersService.findByUserName(username);
            oldUser.setUsername(user.getUsername());
            oldUser.setPassword(user.getPassword());
            usersService.saveNewUser(oldUser);

           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteByUsername(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        usersRepo.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<?> greetings(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse response= weatherService.getWeather("Mumbai");
        String greeting="";
        if(response!=null){
            greeting=" , weather feels like "+response.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi " +authentication.getName() + greeting,HttpStatus.OK);
    }
}
