package com.example.recipionist.recipionistapi.Repositories;

import com.example.recipionist.recipionistapi.Models.Cocktail.CocktailCategory;
import com.example.recipionist.recipionistapi.Models.Meals.MealCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CocktailCategoryRepository extends JpaRepository<CocktailCategory, Long> {
    Optional<CocktailCategory> findCocktailCategoryByCategory(String category);
}
