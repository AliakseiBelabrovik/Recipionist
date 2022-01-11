package com.example.recipionist.recipionistapi.Services;

import com.example.recipionist.recipionistapi.Models.Ingredient.Ingredient;
import com.example.recipionist.recipionistapi.Models.Meals.MealIngredient;
import com.example.recipionist.recipionistapi.Repositories.IngredientRepository;
import com.example.recipionist.recipionistapi.Repositories.MealIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealIngredientService {

    private MealIngredientRepository mealIngredientRepository;

    @Autowired
    public MealIngredientService(MealIngredientRepository mealIngredientRepository) {
        this.mealIngredientRepository = mealIngredientRepository;
    }

    public List<MealIngredient> getAllMealIngredientsFromDatabase() {
        return mealIngredientRepository.findAll();
    }

    public MealIngredient getMealIngredientById(Long id) {
        //TODO: handling if no ingredient found
        return mealIngredientRepository.findById(id).get();
    }



    public void addNewMealIngredientToDatabase(MealIngredient mealIngredient) {


        mealIngredientRepository.save(mealIngredient);
    }

    public void deleteMealIngredientFromDatabase(Long id) {
        boolean exists = mealIngredientRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("MealIngredient with id " + id + " does not exist.");
        }
        mealIngredientRepository.deleteById(id);
    }


}
