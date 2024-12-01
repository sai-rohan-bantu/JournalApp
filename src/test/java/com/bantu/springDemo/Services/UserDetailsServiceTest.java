package com.bantu.springDemo.Services;

import com.bantu.springDemo.Entity.Users;
import com.bantu.springDemo.Repository.UsersRepo;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class UserDetailsServiceTest {

    @InjectMocks
    public UserDetailsService userDetailsService;

    @Mock
    public UsersRepo usersRepo;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testUserDetailsServiceTest(){
        when(usersRepo.findByUsername(ArgumentMatchers.anyString())).thenReturn(Users.builder().username("ram").password("ram").roles(new ArrayList<>()).build());
        UserDetails userDetails=userDetailsService.loadUserByUsername("ram");
        Assertions.assertNotNull(userDetails);
    }
}
