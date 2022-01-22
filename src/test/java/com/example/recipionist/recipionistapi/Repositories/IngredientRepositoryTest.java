package com.example.recipionist.recipionistapi.Repositories;

import com.example.recipionist.recipionistapi.Models.Ingredient.Ingredient;
import com.example.recipionist.recipionistapi.Services.IngredientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class IngredientRepositoryTest {

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    IngredientService ingredientService;

    @Test
    public void printAllIngredients() {
        List<Ingredient> ingredients = ingredientService.getAllIngredientsFromDatabase();
        System.out.println( "Ingredients = " + ingredients );
    }

    @Test
    public void addNewIngredients() {
        Ingredient ingredient1 = Ingredient.builder()
                .ingredientName("Sugar")
                .build();
        Ingredient ingredient2 = Ingredient.builder()
                .ingredientName("Orange")
                .build();
        Ingredient ingredient3 = Ingredient.builder()
                .ingredientName("Potato")
                .build();

        ingredientService.addNewIngredientToDatabase(ingredient1);
        ingredientService.addNewIngredientToDatabase(ingredient2);
        ingredientService.addNewIngredientToDatabase(ingredient3);
    }


    @Test
    public void getMealIngredientsOfIngredient() {
        Ingredient ingredient = ingredientRepository.findIngredientByIngredientNameIgnoreCase("Lemon").get();
        //List<MealIngredient> mealIngredients = ingredient.getMealIngredients();
        //System.out.println("mealIngredients = " + mealIngredients);


    }



}