package com.example.recipionist.recipionistapi.Repositories;

import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import com.example.recipionist.recipionistapi.Models.Meals.MealCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {


    @Query("SELECT m FROM Meal m WHERE m.mealName = ?1")
    Optional<Meal> findByMealName(String mealName);


    ArrayList<Meal> findByMealNameContainingIgnoreCase(String mealName);

    ArrayList<Meal> findByAreaContainingIgnoreCase(String area);

    ArrayList<Meal> findAllByMealCategory(MealCategory mealCategory);

}
