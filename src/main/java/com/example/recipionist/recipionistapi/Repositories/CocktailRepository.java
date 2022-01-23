package com.example.recipionist.recipionistapi.Repositories;

import com.example.recipionist.recipionistapi.Models.Cocktail.Cocktail;
import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CocktailRepository extends JpaRepository<Cocktail, Long> {

    @Query("SELECT c FROM Cocktail c WHERE c.cocktailName = ?1")
    Optional<Cocktail> findByCocktailName(String cocktailName);

}
