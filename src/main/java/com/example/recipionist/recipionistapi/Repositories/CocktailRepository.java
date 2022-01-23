package com.example.recipionist.recipionistapi.Repositories;

import com.example.recipionist.recipionistapi.Models.Cocktail.Cocktail;
import com.example.recipionist.recipionistapi.Models.Cocktail.CocktailCategory;
import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import com.example.recipionist.recipionistapi.Models.Meals.MealCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface CocktailRepository extends JpaRepository<Cocktail, Long> {

    @Query("SELECT c FROM Cocktail c WHERE c.cocktailName = ?1")
    Optional<Cocktail> findByCocktailName(String cocktailName);
    ArrayList<Cocktail> findByGlassContainingIgnoreCase(String glass);
    ArrayList<Cocktail> findByCocktailNameContainingIgnoreCase(String cocktailName);
    ArrayList<Cocktail> findAllByCocktailCategory(CocktailCategory cocktailCategory);
}
