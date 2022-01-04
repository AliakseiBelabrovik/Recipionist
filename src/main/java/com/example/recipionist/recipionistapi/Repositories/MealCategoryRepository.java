package com.example.recipionist.recipionistapi.Repositories;

import com.example.recipionist.recipionistapi.Models.Meals.MealCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MealCategoryRepository extends JpaRepository<MealCategory, Long> {

    Optional<MealCategory> findMealCategoryByCategoryName(String categoryName);


}
