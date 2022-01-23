package com.example.recipionist.recipionistapi.Controllers;

import com.example.recipionist.recipionistapi.Models.Cocktail.Cocktail;
import com.example.recipionist.recipionistapi.Models.Cocktail.CocktailCategory;
import com.example.recipionist.recipionistapi.Models.Cocktail.CocktailGlass;
import com.example.recipionist.recipionistapi.Models.Cocktail.CocktailShort;
import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import com.example.recipionist.recipionistapi.Models.Meals.MealArea;
import com.example.recipionist.recipionistapi.Models.Meals.ShortMeal;
import com.example.recipionist.recipionistapi.Services.CocktailService;
import com.example.recipionist.recipionistapi.Services.MealAreaService;
import com.example.recipionist.recipionistapi.Services.MealCategoryService;
import com.example.recipionist.recipionistapi.Services.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@CrossOrigin
@RestController
public class CocktailController {


    @Autowired
    public CocktailController(
            CocktailService cocktailService
            ) {
        this.cocktailService = cocktailService;

    }

    @Autowired
    private RestTemplate restTemplate;

    private CocktailService cocktailService;
    // private MealCategoryService mealCategoryService = new MealCategoryService();


    @GetMapping("api/recipionist/cocktail/random")
    public Cocktail getARandomCocktail() {
        String data = restTemplate.getForObject("https://www.thecocktaildb.com/api/json/v1/1/random.php", String.class);

        return cocktailService.getSingleCocktail(data);
    }


    @GetMapping("api/recipionist/cocktail/firstletter/{letter}")
    public ArrayList<Cocktail> getByFirstLetter(@PathVariable String letter) {
        String data = restTemplate.getForObject("https://www.thecocktaildb.com/api/json/v1/1/search.php?f="+letter, String.class);

        ArrayList<Cocktail> allCocktails = cocktailService.getCocktails(data);
        allCocktails.addAll(this.cocktailService.getLocalCocktailsByFirstletter(letter));

        return allCocktails;
    }

    @GetMapping("api/recipionist/cocktail/id/{id}")
    public Cocktail getById(@PathVariable String id) {

        //if meal is present in the local DB, get it
        if (cocktailService.isLocalCocktail(Long.parseLong(id))) {
            return cocktailService.getCocktailById(Long.parseLong(id));
        } else {
            //otherwise ask the MealDB
            String data = restTemplate.getForObject("https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=" + id, String.class);
            return cocktailService.getSingleCocktail(data);
        }


    }

    @GetMapping("api/recipionist/cocktail/name/{name}")
    public ArrayList<CocktailShort> getByName(@PathVariable String name) {

        String data = restTemplate.getForObject("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + name, String.class);
        ArrayList<CocktailShort> allCocktails = cocktailService.getCocktailsShort(data); // parsing json data

        allCocktails.addAll(this.cocktailService.getLocalCocktailsByName(name)); // get meals from db

        return allCocktails;
    }


    @GetMapping("api/recipionist/cocktail/ingredient/{ingredient}")
    public ArrayList<CocktailShort> getByIngredient(@PathVariable String ingredient) {
        String data = restTemplate.getForObject("https://www.thecocktaildb.com/api/json/v1/1/filter.php?i="+ingredient, String.class);
        ArrayList<CocktailShort> allCocktails = cocktailService.getCocktailsShort(data);
        allCocktails.addAll(this.cocktailService.getLocalCocktailsByIngredient(ingredient));

        return allCocktails;
    }

    @GetMapping("api/recipionist/cocktail/glass/{glass}")
    public ArrayList<CocktailShort> getByGlass(@PathVariable String glass) {
        String data = restTemplate.getForObject("https://www.thecocktaildb.com/api/json/v1/1/filter.php?g="+glass, String.class);

        ArrayList<CocktailShort> allCocktails = cocktailService.getCocktailsShort(data);// parse data from json
        allCocktails.addAll(this.cocktailService.getLocalCocktailsByGlass(glass)); // get content from db

        return allCocktails;
    }
    @GetMapping("api/recipionist/cocktail/category/{category}")
    public ArrayList<CocktailShort> getByCategory(@PathVariable String category) {

        if(category.equals("Milk")){
            category = "Milk_/_Float_/_Shake ";
        } else if(category.equals("Coffee")){
            category = "Coffee_/_Tea";
        }else if (category.equals("Other")){
            category = "Other/Unknown";
        }else if(category.equals("Punch")){
            category = "Punch_/_Party_Drink";
        } else if(category.equals("Soft")){
            category = "Soft_Drink_/_Soda";
        }

        String data = restTemplate.getForObject("https://www.thecocktaildb.com/api/json/v1/1/filter.php?c=" + category, String.class);
        ArrayList<CocktailShort> allCocktails = cocktailService.getCocktailsShort(data);// parse db data
        allCocktails.addAll(this.cocktailService.getLocalCocktailsByCategory(category));// get data from database

        return allCocktails;
    }


    @GetMapping("api/recipionist/cocktail/categories")
    public ArrayList<CocktailCategory> getCocktailCategories() {
       String data = restTemplate.getForObject("https://www.thecocktaildb.com/api/json/v1/1/list.php?c=list", String.class);

       return cocktailService.getCocktailCategories(data);
    }

    @GetMapping("api/recipionist/cocktail/glasses")
    public ArrayList<CocktailGlass> getCocktailGlasses() {
        String data = restTemplate.getForObject("https://www.thecocktaildb.com/api/json/v1/1/list.php?g=list", String.class);

        ArrayList<CocktailGlass> cocktailGlasses = cocktailService.getCocktailGlasses(data);
        cocktailGlasses.addAll(cocktailService.getLocalCocktailGlasses(cocktailGlasses)); // get database glasses

        return cocktailGlasses;
    }

    @RequestMapping(value = "/api/recipionist/cocktails/new", method = RequestMethod.POST)
    @ResponseBody
    public Cocktail createCocktail(
            Cocktail cocktail
    ) {
        Cocktail cocktailResult = cocktailService.addNewCocktailToDatabase(cocktail);
        System.out.println(cocktailResult);
        return cocktailResult;
    }


    @RequestMapping(value = "/api/recipionist/cocktails/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Cocktail updateCocktail( @PathVariable String id,
                            Cocktail cocktail) {
        return cocktailService.updateCocktail(cocktail, id);
    }

    @RequestMapping(value = "/api/recipionist/cocktails/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteCocktail( @PathVariable String id) {

        return cocktailService.deleteCocktailFromDatabase(Long.parseLong(id));
    }

    @PatchMapping("/api/recipionist/cocktails/update/{id}")
    public Cocktail updateMealPatch(@PathVariable String id,
                                Cocktail cocktail) {
        return cocktailService.updateCocktailPatch(cocktail, Long.parseLong(id));
    }
}
