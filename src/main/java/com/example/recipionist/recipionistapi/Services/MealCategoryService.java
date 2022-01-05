package com.example.recipionist.recipionistapi.Services;

import com.example.recipionist.recipionistapi.DataLoader;
import com.example.recipionist.recipionistapi.Models.Meals.MealCategory;
import com.example.recipionist.recipionistapi.Models.User.User;
import com.example.recipionist.recipionistapi.Repositories.MealCategoryRepository;
import com.example.recipionist.recipionistapi.Repositories.UserRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MealCategoryService {

    private MealCategoryRepository mealCategoryRepository;

    DataLoader dataLoader = new DataLoader();

    @Autowired
    public MealCategoryService(MealCategoryRepository mealCategoryRepository) {
        this.mealCategoryRepository = mealCategoryRepository;
    }


    public MealCategory getMealCategoryById(Long id) {
        return mealCategoryRepository.findById(id).get();
    }


    public List<MealCategory> getAllCategoriesFromDatabase() {
        return mealCategoryRepository.findAll();
    }

    public void addNewMealCategoryInDatabase(MealCategory mealCategory) {

        Optional<MealCategory> mealCategoryOptional =
                mealCategoryRepository.findMealCategoryByCategoryName(mealCategory.getCategoryName());

        System.out.println("Optional user is " + mealCategoryOptional);
        if (mealCategoryOptional.isPresent()) {
            System.out.println("Category name taken by " + mealCategoryOptional);
            throw new IllegalStateException("Category name taken");
        }
        System.out.println("Adding new category " + mealCategory);


        mealCategoryRepository.save(mealCategory);
    }

    public void deleteMealCategoryFromDatabase(Long id) {
        boolean exists = mealCategoryRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Meal category with id " + id + " does not exist.");
        }
        mealCategoryRepository.deleteById(id);
    }



    public ArrayList<MealCategory> getMealCategories(String data){

        JSONObject mealObj = dataLoader.loadData(data);
        JSONArray jsonArray;
        if (mealObj.containsKey("categories")) {
            jsonArray= (JSONArray) mealObj.remove("categories");
        } else {
            jsonArray= (JSONArray) mealObj.remove("meals");
        }

        ArrayList<MealCategory> categoryList = new ArrayList<>();

        while (!jsonArray.isEmpty()){

            JSONObject category = (JSONObject) jsonArray.get(jsonArray.size()-1);
            jsonArray.remove(jsonArray.size()-1);

            MealCategory mealCategory = new MealCategory(
                    Long.parseLong((String) category.get("idCategory")),
                    (String) category.get("strCategory"),
                    (String) category.get("strCategoryThumb"),
                    (String) category.get("strCategoryDescription"),
                    (String) category.get("strArea")
            );
           categoryList.add(mealCategory);

        }

        return categoryList;
    }
}
