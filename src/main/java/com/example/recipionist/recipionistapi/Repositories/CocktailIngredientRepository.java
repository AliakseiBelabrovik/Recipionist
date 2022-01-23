package com.example.recipionist.recipionistapi.Repositories;

import com.example.recipionist.recipionistapi.Models.Cocktail.Cocktail;
import com.example.recipionist.recipionistapi.Models.Cocktail.CocktailIngredient;
import com.example.recipionist.recipionistapi.Models.Ingredient.Ingredient;
import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import com.example.recipionist.recipionistapi.Models.Meals.MealIngredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CocktailIngredientRepository extends JpaRepository<CocktailIngredient, Long> {
    Optional<CocktailIngredient> findByIngredientAndCocktail(Ingredient ingredient, Cocktail cocktail);



}

