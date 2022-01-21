package com.example.recipionist.recipionistapi.Services;

import com.example.recipionist.recipionistapi.Models.Ingredient.Ingredient;
import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import com.example.recipionist.recipionistapi.Models.Meals.MealIngredient;
import com.example.recipionist.recipionistapi.Repositories.IngredientRepository;
import com.example.recipionist.recipionistapi.Repositories.MealIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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


    public void updateMeal(Long id, Meal meal) {
        mealIngredientRepository.updateMealById(id, meal);
    }

    /**
     * Method to delete MealIngredients from Database, which relate to a particular meal,
     * that we are going to delete too
     * @param meal -> Meal to be deleted
     */
    public void deleteMealIngredientsRelatedToMeal(Meal meal) {

        List<MealIngredient> mealIngredientList = mealIngredientRepository.findMealIngredientsByMeal(meal);
        for (int i = 0; i < mealIngredientList.size(); i++) {
            MealIngredient mealIngredient = mealIngredientList.get(i);
            mealIngredient.setIngredient(null); //delete foreign key to an ingredient
            mealIngredient.setMeal(null); //delete foreign key the meal

            //then, delete the mealIngredient itself
            deleteMealIngredientFromDatabase(mealIngredient.getId());
        }



    }
}
