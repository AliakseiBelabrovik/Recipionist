package com.example.recipionist.recipionistapi.Repositories;

import com.example.recipionist.recipionistapi.Models.Ingredient.Ingredient;
import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import com.example.recipionist.recipionistapi.Models.Meals.MealCategory;
import com.example.recipionist.recipionistapi.Models.Meals.MealIngredient;
import com.example.recipionist.recipionistapi.Models.User.User;
import com.example.recipionist.recipionistapi.Services.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MealIngredientRepositoryTest {

    @Autowired
    MealIngredientService mealIngredientService;

    @Autowired
    MealCategoryService mealCategoryService;
    @Autowired
    IngredientService ingredientService;
    @Autowired
    UserService userService;
    @Autowired
    MealRepository mealRepository;

    @Autowired
    IngredientRepository ingredientRepository;


    @Test
    public void addNewMealIngredients() {

        //TODO create meal and ingredients
        //TODO then create mealIngredients and test all this

        Ingredient ingredient1 = Ingredient.builder()
                .ingredientName("Lemon")
                .build();
        Ingredient ingredient2 = Ingredient.builder()
                .ingredientName("Pesto")
                .build();
        Ingredient ingredient3 = Ingredient.builder()
                .ingredientName("Salt")
                .build();
        Ingredient ingredient4 = Ingredient.builder()
                .ingredientName("Paprika")
                .build();

        ingredientService.addNewIngredientToDatabase(ingredient1);
        ingredientService.addNewIngredientToDatabase(ingredient2);
        ingredientService.addNewIngredientToDatabase(ingredient3);
        ingredientService.addNewIngredientToDatabase(ingredient4);

        ingredient1 = ingredientRepository.findIngredientByIngredientName(ingredient1.getIngredientName()).get();
        ingredient2 = ingredientRepository.findIngredientByIngredientName(ingredient2.getIngredientName()).get();
        ingredient3 = ingredientRepository.findIngredientByIngredientName(ingredient3.getIngredientName()).get();
        ingredient4 = ingredientRepository.findIngredientByIngredientName(ingredient4.getIngredientName()).get();


        User user1 = User.builder()
                .firstName("S")
                .lastName("A")
                .email("blabla@example.com")
                .password("12345678")
                .build();
        userService.addNewUser(user1);


        MealCategory mealCategory = MealCategory.builder()
                .categoryName("Breakfast")
                .description("blabla")
                .thumbnail("sfsfsf")
                .strArea("sfsfdssf")
                .build();
        mealCategoryService.addNewMealCategoryInDatabase(mealCategory);
        MealCategory mealCategory1 = mealCategoryService.getAllCategoriesFromDatabase().get(0);

        Meal meal = Meal.builder()
                .mealName("My tasty dish")
                .area("British")
                //.category("Breakfast")
                .mealCategory(mealCategory)
                .instructions("Do something with it")
                .tags("Bla Bla Bla")
                .thumbnail("URL to a photo")
                .youtubeLink("Link to a youtube video")
                .user(user1)
                .build();


        mealRepository.save(meal);

        Meal newMeal = mealRepository.findByMealName(meal.getMealName());

        MealIngredient mealIngredient1 = MealIngredient.builder()
                .meal(newMeal)
                .ingredient(ingredient1)
                .measure("2 pieces")
                .build();
        MealIngredient mealIngredient2 = MealIngredient.builder()
                .meal(newMeal)
                .ingredient(ingredient2)
                .measure("10 g")
                .build();
        MealIngredient mealIngredient3 = MealIngredient.builder()
                .meal(newMeal)
                .ingredient(ingredient3)
                .measure("1 g")
                .build();
        MealIngredient mealIngredient4 = MealIngredient.builder()
                .meal(newMeal)
                .ingredient(ingredient4)
                .measure("50 g")
                .build();

        mealIngredientService.addNewMealIngredientToDatabase(mealIngredient1);
        mealIngredientService.addNewMealIngredientToDatabase(mealIngredient2);
        mealIngredientService.addNewMealIngredientToDatabase(mealIngredient3);
        mealIngredientService.addNewMealIngredientToDatabase(mealIngredient4);


    }


}