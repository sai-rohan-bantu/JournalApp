package com.bantu.springDemo.Services;

import com.bantu.springDemo.Entity.JournalEntry;
import com.bantu.springDemo.Entity.Users;
import com.bantu.springDemo.Repository.UsersRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UsersService {

    private final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();


    @Autowired
    public UsersRepo usersRepo;

    public void saveUser(Users user){
        usersRepo.save(user);
    }

    public boolean saveNewUser(Users user){
        try{
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        usersRepo.save(user);
        return true;
        }
        catch (Exception e){
            log.error("sorry can't create user with above details {} ",user.getUsername(),e);
            return false;
        }
    }

    public List<Users> getUsers(){
        return usersRepo.findAll();
    }


    public Optional<Users> findByID(ObjectId id){
        return usersRepo.findById(id);
    }

    public void deleteById(ObjectId id){
        usersRepo.deleteById(id);
    }

    public Users findByUserName(String username){
        return usersRepo.findByUsername(username);
    }

    public void saveAdmin(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        usersRepo.save(user);
    }
}
