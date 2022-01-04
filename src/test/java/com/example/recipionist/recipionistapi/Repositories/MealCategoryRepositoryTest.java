package com.example.recipionist.recipionistapi.Repositories;

import com.example.recipionist.recipionistapi.Models.Meals.MealCategory;
import com.example.recipionist.recipionistapi.Models.User.User;
import com.example.recipionist.recipionistapi.Services.MealCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MealCategoryRepositoryTest {

    @Autowired
    private MealCategoryRepository mealCategoryRepository;

    @Autowired
    private MealCategoryService mealCategoryService;

    @Test
    public void addNewMealCategories() {

        MealCategory mealCategory1 = MealCategory.builder()
                .categoryName("Pasta")
                .description("Very tasty category")
                .strArea("Belarusian")
                .thumbnail("https://www.google.com/" +
                        "url?sa=i&url")
                .build();
        MealCategory mealCategory2 = MealCategory.builder()
                .categoryName("Breakfast")
                .description("Very good")
                .thumbnail("URL to the thumbnail")
                .build();

        mealCategoryService.addNewMealCategoryInDatabase(mealCategory1);
        mealCategoryService.addNewMealCategoryInDatabase(mealCategory2);

    }
    @Test
    public void printAllCategoriesInDatabase() {
        List<MealCategory> mealCategories = mealCategoryService.getAllCategoriesFromDatabase();
        System.out.println("MealCategories = " + mealCategories);
    }


}