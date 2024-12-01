package com.bantu.springDemo.UserRepoImplTest;

import com.bantu.springDemo.Services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestSendEmail {

    @Autowired
    public EmailService emailService;

    @Test
    public void testSendEmail(){
        emailService.sendEmail("rohansiri2001@gmail.com","Testing java email sender","Namaste Sai Rohan");
    }
}
