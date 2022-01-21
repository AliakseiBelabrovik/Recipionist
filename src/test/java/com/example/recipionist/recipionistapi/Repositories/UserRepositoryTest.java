package com.example.recipionist.recipionistapi.Repositories;

import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import com.example.recipionist.recipionistapi.Models.User.User;
import com.example.recipionist.recipionistapi.Services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    MealRepository mealRepository;


    /*
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


     */

    @Test
    public void getUsers() {
        List<User> users = userService.getAllUsers();
        System.out.println("Users = " + users);
    }


    @Test
    public void createUser() {
        User user = User.builder()
                .firstName("Santa")
                .lastName("Claus")
                .email("santa@example.com")
                .password("christmas")
                .build();
        userRepository.save(user);

    }

    @Test
    public void updateUser() {
        userRepository.updateStudentNameByEmail("Donald", "santa@example.com");
    }

    @Test
    public void assignMealToUser() {

        User user = userRepository.findById(2L).get();

        /*
        Meal meal = Meal.builder()
                .mealName("SantaMeal")
                .area("Finnland")
                .instructions("Just do it")
                .user(user)
                .build();
        mealRepository.save(meal);

         */
        /*
        //user.setMeals(new ArrayList<Meal>());
        List<Meal> meals = user.getMeals();
        //String area = meals.get(1).getArea();
        System.out.println("meals = " + meals);
        Meal meal = mealRepository.findByMealName("SantaMeal").get();
        System.out.println("meal = " + meal);
        
        
        user.addMeal(meal);
        //System.out.println("meals = " + meals);

         */
    }

    @Test
    public void printMealsOfUser() {
        User user = userRepository.findUserByEmail("santa@example.com").get();
        //System.out.println("Meals of the user " + user.getLastName() + " are " + user.getMeals());


    }


}