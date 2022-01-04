package com.example.recipionist.recipionistapi.Repositories;

import com.example.recipionist.recipionistapi.Models.Ingredient.Ingredient;
import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import com.example.recipionist.recipionistapi.Models.Meals.MealCategory;
import com.example.recipionist.recipionistapi.Models.Meals.MealIngredient;
import com.example.recipionist.recipionistapi.Services.MealCategoryService;
import com.example.recipionist.recipionistapi.Services.MealIngredientService;
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

        MealCategory mealCategory = mealCategoryService.getAllCategoriesFromDatabase().get(0);

        Meal meal = Meal.builder()
                .mealName("My tasty dish")
                .area("British")
                .category("Breakfast")
                .mealCategory(mealCategory)
                .instructions("Do something with it")
                .tags("Bla Bla Bla")
                .thumbnail("URL to a photo")
                .youtubeLink("Link to a youtube video")
                .build();


        MealIngredient mealIngredient1 = MealIngredient.builder()
                .meal(meal)
                .ingredient(ingredient1)
                .measure("2 pieces")
                .build();
        MealIngredient mealIngredient2 = MealIngredient.builder()
                .meal(meal)
                .ingredient(ingredient2)
                .measure("10 g")
                .build();
        MealIngredient mealIngredient3 = MealIngredient.builder()
                .meal(meal)
                .ingredient(ingredient3)
                .measure("1 g")
                .build();
        MealIngredient mealIngredient4 = MealIngredient.builder()
                .meal(meal)
                .ingredient(ingredient4)
                .measure("50 g")
                .build();

        mealIngredientService.addNewMealIngredientToDatabase(mealIngredient1);
        mealIngredientService.addNewMealIngredientToDatabase(mealIngredient2);
        mealIngredientService.addNewMealIngredientToDatabase(mealIngredient3);
        mealIngredientService.addNewMealIngredientToDatabase(mealIngredient4);


    }


}