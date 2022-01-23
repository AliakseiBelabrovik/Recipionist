package com.example.recipionist.recipionistapi.Controllers;

import com.example.recipionist.recipionistapi.Models.User.User;
import com.example.recipionist.recipionistapi.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="api/recipionist/user")
public class UserController {

    private final UserService userService;

    @Autowired //says that userService should be automatically instanciated for us
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/session/currentuser", method = RequestMethod.GET)
    public String checkCurrentUser() {
        /*
        AbstractAuthenticationToken auth =
                (AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();


        User currentUser = (User) auth.getDetails();

         */
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("currentUser = " + currentUser);
        System.out.println("name = " + currentUser.getFirstName());
        return currentUser.getFirstName();
    }

    /*
    @GetMapping(path ="test")
    public List<User> getUsers() {
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

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication();

        return userService.getAllUsers();


    }
 */


}
