package com.bantu.springDemo.UserRepoImplTest;

import com.bantu.springDemo.Repository.UsersRepoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepoTests {

    @Autowired
    public UsersRepoImpl usersRepo;

    @Test
    public void testGetUserForSA(){
        usersRepo.getUserForSA();
    }
    @Test
    public void testGetUserForSAandEmail(){
        usersRepo.getUsersWithEmailAndPassword();
    }

}
