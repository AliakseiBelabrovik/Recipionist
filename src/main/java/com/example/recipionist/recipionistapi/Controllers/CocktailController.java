package com.example.recipionist.recipionistapi.Controllers;

import com.example.recipionist.recipionistapi.Models.Cocktail.Cocktail;
import com.example.recipionist.recipionistapi.Models.Cocktail.CocktailCategory;
import com.example.recipionist.recipionistapi.Models.Cocktail.CocktailGlass;
import com.example.recipionist.recipionistapi.Models.Cocktail.CocktailShort;
import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import com.example.recipionist.recipionistapi.Models.Meals.ShortMeal;
import com.example.recipionist.recipionistapi.Services.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@CrossOrigin
@RestController
public class CocktailController {

    @Autowired
    private RestTemplate restTemplate;

    private CocktailService cocktailService = new CocktailService();
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
        String data = restTemplate.getForObject("https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=" + id, String.class);

        return cocktailService.getSingleCocktail(data, id);
    }

    @GetMapping("api/recipionist/cocktail/name/{name}")
    public ArrayList<CocktailShort> getByName(@PathVariable String name) {

        String data = restTemplate.getForObject("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + name, String.class);
        ArrayList<CocktailShort> allCocktails = cocktailService.getCocktailsShort(data);
        allCocktails.addAll(this.cocktailService.getLocalCocktailsByName(name));

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

        ArrayList<CocktailShort> allCocktails = cocktailService.getCocktailsShort(data);
        allCocktails.addAll(this.cocktailService.getLocalCocktailsByGlass(glass));

        return allCocktails;
    }
    @GetMapping("api/recipionist/cocktail/category/{category}")
    public ArrayList<CocktailShort> getByCategory(@PathVariable String category) {
        String data = restTemplate.getForObject("https://www.thecocktaildb.com/api/json/v1/1/filter.php?c=" + category, String.class);
        ArrayList<CocktailShort> allCocktails = cocktailService.getCocktailsShort(data);
        allCocktails.addAll(this.cocktailService.getLocalCocktailsByCategory(category));

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

        return cocktailService.getCocktailGlasses(data);
    }

    @RequestMapping(value = "/api/recipionist/cocktails/new", method = RequestMethod.POST)
    @ResponseBody
    public Cocktail createCocktail(
            Cocktail cocktail
    ) {
        Cocktail cocktailResult = cocktailService.saveCocktail(cocktail);
        System.out.println(cocktail);
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
        return cocktailService.deleteCocktail(id);
    }

    @PatchMapping("/api/recipionist/cocktails/update/{id}")
    public Cocktail updateMealPatch(@PathVariable String id,
                                Cocktail cocktail) {
        return cocktailService.updateCocktailPatch(cocktail, id);
    }
}
