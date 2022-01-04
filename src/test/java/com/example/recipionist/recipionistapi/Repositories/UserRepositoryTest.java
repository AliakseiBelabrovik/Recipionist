package com.example.recipionist.recipionistapi.Repositories;

import com.example.recipionist.recipionistapi.Models.User.User;
import com.example.recipionist.recipionistapi.Services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    public void addNewUsers() {
        User user1 = User.builder()
                .firstName("Alexandra")
                .lastName("Test")
                .email("al@example.com")
                .password("111111")
                .build();
        User user2 = User.builder()
                .firstName("Papa")
                .lastName("Roach")
                .email("hahaaaa@example.com")
                .password("dfsfsdf")
                .build();
        //User user1 = new User("Alexandra", "Test", "al@example.com", "111111");
        //User user2 = new User("Papa", "Roach", "hahaaaa@example.com", "dfsfsdf");
        userService.addNewUser(user1);
        userService.addNewUser(user2);
    }


    @Test
    public void getUsers() {
        List<User> users = userService.getAllUsers();
        System.out.println("Users = " + users);
    }


}