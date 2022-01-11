package com.example.recipionist.recipionistapi.Repositories;

import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {


    @Query("SELECT m FROM Meal m WHERE m.mealName = ?1")
    Optional<Meal> findByMealName(String mealName);


}
