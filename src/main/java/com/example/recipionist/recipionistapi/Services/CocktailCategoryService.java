package com.example.recipionist.recipionistapi.Services;

import com.example.recipionist.recipionistapi.DataLoader;
import com.example.recipionist.recipionistapi.Models.Cocktail.CocktailCategory;
import com.example.recipionist.recipionistapi.Repositories.CocktailCategoryRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CocktailCategoryService {

    private CocktailCategoryRepository cocktailCategoryRepository;

    DataLoader dataLoader = new DataLoader();

    @Autowired
    public CocktailCategoryService(CocktailCategoryRepository cocktailCategoryRepository) {
        this.cocktailCategoryRepository = cocktailCategoryRepository;
    }


    public CocktailCategory getCocktailCategoryById(Long id) {
        return cocktailCategoryRepository.findById(id).get();
    }


    public List<CocktailCategory> getAllCategoriesFromDatabase() {
        return cocktailCategoryRepository.findAll();
    }

    public CocktailCategory getCocktailCategoryByName(String categoryName) {
        Optional<CocktailCategory> optionalCocktailCategory =
                cocktailCategoryRepository.findCocktailCategoryByCategoryName(categoryName);
        return optionalCocktailCategory
                .orElseThrow(() -> new IllegalStateException("No category with name " + categoryName + " was found."));
    }

    public void addNewCocktailCategoryInDatabase(CocktailCategory cocktailCategory) {

        Optional<CocktailCategory> cocktailCategoryOptional =
                cocktailCategoryRepository.findCocktailCategoryByCategoryName(cocktailCategory.getCategory());

        System.out.println("Optional user is " + cocktailCategoryOptional);
        if (cocktailCategoryOptional.isPresent()) {
            System.out.println("Category name taken by " + cocktailCategoryOptional);
            throw new IllegalStateException("Category name taken");
        }
        System.out.println("Adding new category " + cocktailCategory);


        cocktailCategoryRepository.save(cocktailCategory);
    }

    public void deleteCocktailCategoryFromDatabase(Long id) {
        boolean exists = cocktailCategoryRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Cocktail category with id " + id + " does not exist.");
        }
        cocktailCategoryRepository.deleteById(id);
    }



    public ArrayList<CocktailCategory> getCocktailCategories(String data) {

        JSONObject cocktailObj = dataLoader.loadData(data);
        JSONArray jsonArray;
        if (cocktailObj.containsKey("categories")) {
            jsonArray = (JSONArray) cocktailObj.remove("categories");
        } else {
            jsonArray = (JSONArray) cocktailObj.remove("cocktails");
        }

        ArrayList<CocktailCategory> categoryList = new ArrayList<>();

        while (!jsonArray.isEmpty()) {

            JSONObject category = (JSONObject) jsonArray.get(jsonArray.size() - 1);
            jsonArray.remove(jsonArray.size() - 1);

            CocktailCategory cocktailCategory = new CocktailCategory(
                    (String) category.get("strCategory")
            );
            categoryList.add(cocktailCategory);

        }

        return categoryList;
    }
    
}
