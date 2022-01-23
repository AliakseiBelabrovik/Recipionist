package com.example.recipionist.recipionistapi.Repositories;

import com.example.recipionist.recipionistapi.Models.Cocktail.Cocktail;
import com.example.recipionist.recipionistapi.Models.Cocktail.CocktailIngredient;
import com.example.recipionist.recipionistapi.Models.Ingredient.Ingredient;
import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import com.example.recipionist.recipionistapi.Models.Meals.MealIngredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CocktailIngredientRepository extends JpaRepository<CocktailIngredient, Long> {
    Optional<CocktailIngredient> findByIngredientAndCocktail(Ingredient ingredient, Cocktail cocktail);

    List<CocktailIngredient> findCocktailIngredientsByIngredient(Ingredient ingredient);
    List<CocktailIngredient> findCocktailIngredientsByCocktail(Cocktail cocktail);


    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "UPDATE cocktail_ingredients SET cocktail_id = ?2 WHERE id = ?1"
    )
    int updateCocktailOfCocktailIngredient(Long cocktailIngredientId, Cocktail cocktail);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "UPDATE cocktail_ingredients SET measure = ?2 WHERE id = ?1"
    )
    int updateMeasureByCocktailIngredientId(Long cocktailIngredientId, String measure);


}

