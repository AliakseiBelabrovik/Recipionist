package com.example.recipionist.recipionistapi.Repositories;

import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import com.example.recipionist.recipionistapi.Models.Meals.MealCategory;
import com.example.recipionist.recipionistapi.Models.Meals.MealIngredient;
import com.example.recipionist.recipionistapi.Models.User.User;
import com.example.recipionist.recipionistapi.Registration.RegistrationRequest;
import com.example.recipionist.recipionistapi.Registration.RegistrationService;
import com.example.recipionist.recipionistapi.Services.MealCategoryService;
import com.example.recipionist.recipionistapi.Services.MealIngredientService;
import com.example.recipionist.recipionistapi.Services.MealService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MealRepositoryTest {

    @Autowired
    MealRepository mealRepository;

    @Autowired
    MealCategoryService mealCategoryService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MealIngredientService mealIngredientService;

    @Autowired
    MealIngredientRepository mealIngredientRepository;

    @Autowired
    RegistrationService registrationService;


    @Test
    public void createMeal() {

        User user = User.builder()
                .firstName("Mama")
                .lastName("Roach")
                .email("test@example.com")
                .password("69696969696")
                .build();

        MealIngredient mealIngredient1 = mealIngredientService.getMealIngredientById(1L);
        System.out.println("mealIngredient1 = " + mealIngredient1);
        MealIngredient mealIngredient2 = mealIngredientService.getMealIngredientById(2L);
        System.out.println("mealIngredient2 = " + mealIngredient2);

        MealCategory mealCategory = mealCategoryService.getMealCategoryById(1L);
        System.out.println("mealCategory = " + mealCategory);
        Meal meal = Meal.builder()
                .mealCategory(mealCategory)
                .mealName("TestMeal")
                .area("Asia")
                .instructions("Do smth with it")
                .mealIngredients(Arrays.asList(mealIngredient1, mealIngredient2))
                .build();

        //for consistence, because bidirectional relationships
        meal.setUser(user);
        user.addMeal(meal);

        mealIngredient1.setMeal(meal);
        mealIngredient2.setMeal(meal);

        meal.addMealIngredient(mealIngredient1);
        meal.addMealIngredient(mealIngredient2);
        List<Meal> mealsAssociatedWithCategory = mealCategory.getMeals(); //firstly, load the meals, then you can use them
        mealCategory.addMeal(meal);


        //saving parent: user, the meal will be saved with him
        userRepository.save(user);

        //Updates entry for mealIngredient with new information: meal_id
        mealIngredientRepository.save(mealIngredient1);
        mealIngredientRepository.save(mealIngredient2);

    }


    @Test
    public void createUserAndMeal() {

        RegistrationRequest request = new RegistrationRequest(
                "Tywin", "Lannister", "tywin.lannister@gmail.com", "password");
        registrationService.register(request);
        User user = userRepository.findById(1L).get();






    }



}