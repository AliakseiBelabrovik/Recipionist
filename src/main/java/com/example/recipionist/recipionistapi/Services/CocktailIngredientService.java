package com.example.recipionist.recipionistapi.Services;

import com.example.recipionist.recipionistapi.Models.Cocktail.Cocktail;
import com.example.recipionist.recipionistapi.Models.Cocktail.CocktailIngredient;
import com.example.recipionist.recipionistapi.Models.Ingredient.Ingredient;
import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import com.example.recipionist.recipionistapi.Models.Meals.MealIngredient;
import com.example.recipionist.recipionistapi.Repositories.CocktailIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * @param cocktail -> takes cocktail as a parameter
     * @return list of measures (string), that relate to this cocktail
     */
    public ArrayList<String> getMeasures(Cocktail cocktail) {
        return findCocktailIngredientsByCocktail(cocktail)
                .stream()
                .map(CocktailIngredient::getMeasure)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Finds all meal ingredients that relate to a particular meal
     * @param cocktail
     * @return list of cocktail ingredients
     */
    public List<CocktailIngredient> findCocktailIngredientsByCocktail(Cocktail cocktail) {
        return cocktailIngredientRepository.findCocktailIngredientsByCocktail(cocktail);
    }

    public List<Cocktail> getCocktailByCocktailIngredient(List<CocktailIngredient> cocktailIngredientList) {
        //Find CocktailIngredients that have this ingredient
        //then find Cocktails that have these CocktailIngredients
        List<Cocktail> cocktails = new ArrayList<>();
        for (int i = 0; i < cocktailIngredientList.size(); i++) {
            cocktails.add(cocktailIngredientList.get(i).getCocktail());
        }
        return cocktails;
    }

    public List<CocktailIngredient> findCocktailIngredientsByIngredient(Ingredient ingredient) {
        return cocktailIngredientRepository.findCocktailIngredientsByIngredient(ingredient);
    }
    public void updateMeasureOfCocktailIngredient(Long cocktailIngredientId, String measure) {
        cocktailIngredientRepository.updateMeasureByCocktailIngredientId(cocktailIngredientId, measure);
    }

    /**
     * This method returns the ingredient names of all ingredients that relate to a particular meal
     * @param cocktail
     * @return list of ingredient names
     */
    public ArrayList<String> getCocktailIngredientsAsListOfStrings(Cocktail cocktail) {
        return findCocktailIngredientsByCocktail(cocktail)
                .stream()
                .map(CocktailIngredient::getIngredient)
                .map(Ingredient::getIngredientName)
                .collect(Collectors.toCollection(ArrayList::new));
    }



    public CocktailIngredient createCocktailIngredient(String measure, Ingredient ingredient, Cocktail cocktail) {
        if (cocktail == null) {
            return CocktailIngredient.builder()
                    .measure(measure)
                    .ingredient(ingredient)
                    .build();
        } else {
            return CocktailIngredient.builder()
                    .measure(measure)
                    .ingredient(ingredient)
                    .cocktail(cocktail)
                    .build();
        }

    }

    public void deleteCocktailIngredientsRelatedToCocktail(Cocktail cocktail) {

        List<CocktailIngredient> cocktailIngredientList = findCocktailIngredientsByCocktail(cocktail);
        for (int i = 0; i < cocktailIngredientList.size(); i++) {
            CocktailIngredient cocktailIngredient = cocktailIngredientList.get(i);
            cocktailIngredient.setIngredient(null); //delete foreign key to an ingredient
            cocktailIngredient.setCocktail(null); //delete foreign key the meal

            //then, delete the cocktailIngredient itself
            deleteCocktailIngredientFromDatabase(cocktailIngredient.getId());
        }
    }

    //upgrades cocktail_id column for a particular cocktail ingredient
    public void updateCocktailOfCocktailIngredient(Long cocktailIngredientId, Cocktail cocktail) {
        cocktailIngredientRepository.updateCocktailOfCocktailIngredient(cocktailIngredientId, cocktail);
    }

}
