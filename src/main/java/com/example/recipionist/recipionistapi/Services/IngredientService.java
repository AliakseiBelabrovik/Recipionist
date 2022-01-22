package com.example.recipionist.recipionistapi.Services;

import com.example.recipionist.recipionistapi.Models.Ingredient.Ingredient;
import com.example.recipionist.recipionistapi.Repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> getAllIngredientsFromDatabase() {
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredientFromDatabaseByName(String ingredientName) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findIngredientByIngredientNameIgnoreCase(ingredientName);
        return optionalIngredient.orElseThrow(() -> new IllegalStateException("There is no ingredient with name " + ingredientName));
        //return optionalIngredient.orElse(null);
    }


    public void addNewIngredientToDatabase(Ingredient ingredient) {

        Optional<Ingredient> ingredientOptional =
                ingredientRepository.findIngredientByIngredientNameIgnoreCase(ingredient.getIngredientName());

        System.out.println("Optional ingredient is " + ingredientOptional);
        if (ingredientOptional.isPresent()) {
            System.out.println("Ingredient name taken by " + ingredientOptional);
            throw new IllegalStateException("Ingredient name taken");
        }
        System.out.println("Adding new ingredient " + ingredient);
        ingredientRepository.save(ingredient);
    }

    public void deleteIngredientFromDatabase(Long id) {
        boolean exists = ingredientRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Ingredient with id " + id + " does not exist.");
        }
        ingredientRepository.deleteById(id);
    }

    public Ingredient createNewIngredientInDatabase(String ingredientName) {
        try {
            Ingredient ingredient = Ingredient
                    .builder()
                    .ingredientName(ingredientName)
                    .build();
            addNewIngredientToDatabase(ingredient);
            return getIngredientFromDatabaseByName(ingredientName);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return getIngredientFromDatabaseByName(ingredientName);
        }

    }

    public Ingredient createIngredient(String ingredientName) {
        return Ingredient
                .builder()
                .ingredientName(ingredientName)
                .build();
    }
}
