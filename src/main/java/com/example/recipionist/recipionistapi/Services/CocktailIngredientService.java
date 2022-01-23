package com.example.recipionist.recipionistapi.Services;

import com.example.recipionist.recipionistapi.Models.Cocktail.CocktailIngredient;
import com.example.recipionist.recipionistapi.Repositories.CocktailIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CocktailIngredientService {
    private CocktailIngredientRepository cocktailIngredientRepository;

    @Autowired
    public CocktailIngredientService(CocktailIngredientRepository cocktailIngredientRepository) {
        this.cocktailIngredientRepository = cocktailIngredientRepository;
    }

    public List<CocktailIngredient> getAllCocktailIngredientsFromDatabase() {
        return cocktailIngredientRepository.findAll();
    }

    public CocktailIngredient getCocktailIngredientById(Long id) {
        //TODO: handling if no ingredient found
        return cocktailIngredientRepository.findById(id).get();
    }



    public void addNewCocktailIngredientToDatabase(CocktailIngredient cocktailIngredient) {


        cocktailIngredientRepository.save(cocktailIngredient);
    }

    public void deleteCocktailIngredientFromDatabase(Long id) {
        boolean exists = cocktailIngredientRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("CocktailIngredient with id " + id + " does not exist.");
        }
        cocktailIngredientRepository.deleteById(id);
    }
}
