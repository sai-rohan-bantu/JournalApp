package com.bantu.springDemo.UserRepoImplTest;

import com.bantu.springDemo.Scheduler.UserScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSATest {

    @Autowired
    public UserScheduler userScheduler;

    @Test
    public void testfetchUsersAndSendSA(){
        userScheduler.fetchUsersAndSendSA();
    }
}
