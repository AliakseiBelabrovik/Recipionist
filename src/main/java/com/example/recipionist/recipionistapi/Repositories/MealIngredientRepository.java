package com.example.recipionist.recipionistapi.Repositories;

import com.example.recipionist.recipionistapi.Models.Ingredient.Ingredient;
import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import com.example.recipionist.recipionistapi.Models.Meals.MealIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MealIngredientRepository extends JpaRepository<MealIngredient, Long> {

    Optional<MealIngredient> findByIngredientAndMeal(Ingredient ingredient, Meal meal);
    //Optional<MealIngredient> findMealIngredientByMeal(Meal meal);


    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "UPDATE meal_ingredients SET meal_id = ?2 WHERE id = ?1"
    )
    int updateMealById(Long id, Meal meal);



    List<MealIngredient> findMealIngredientsByMeal(Meal meal);

}
