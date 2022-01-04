package com.example.recipionist.recipionistapi.Controllers;

import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import com.example.recipionist.recipionistapi.Models.Meals.MealArea;
import com.example.recipionist.recipionistapi.Models.Meals.MealCategory;
import com.example.recipionist.recipionistapi.Models.Meals.ShortMeal;
import com.example.recipionist.recipionistapi.Services.MealAreaService;
import com.example.recipionist.recipionistapi.Services.MealCategoryService;
import com.example.recipionist.recipionistapi.Services.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class MealController {

    @Autowired
    private RestTemplate restTemplate;

    /*
    private MealService mealService = new MealService();
    private MealCategoryService mealCategoryService = new MealCategoryService();
    private MealAreaService mealAreaService = new MealAreaService();

     */
    private final MealService mealService;
    private final MealCategoryService mealCategoryService;
    private final MealAreaService mealAreaService;

    @Autowired
    public MealController(
            MealService mealService,
            MealCategoryService mealCategoryService,
            MealAreaService mealAreaService) {
        this.mealService = mealService;
        this.mealCategoryService = mealCategoryService;
        this.mealAreaService = mealAreaService;
    }



    @GetMapping("api/recipionist/meals/random")
    public Meal getARandomMeal() {
        String data = restTemplate.getForObject("https://www.themealdb.com/api/json/v1/1/random.php", String.class);

        return mealService.getSingleMeal(data);
    }

    @GetMapping("api/recipionist/meals/firstletter/{letter}")
    public ArrayList<Meal> getByFirstLetter(@PathVariable String letter) {
       String data = restTemplate.getForObject("https://www.themealdb.com/api/json/v1/1/search.php?f="+letter, String.class);
        ArrayList<Meal> allMeals = mealService.getMeals(data);
        allMeals.addAll(this.mealService.getLocalMealsByFirstletter(letter));

        return allMeals;
    }

    @GetMapping("api/recipionist/meals/id/{id}")
    public Meal getById(@PathVariable String id) {
        String data = restTemplate.getForObject("https://www.themealdb.com/api/json/v1/1/lookup.php?i="+id, String.class);

        return mealService.getSingleMeal(data, id);
    }

    @GetMapping("api/recipionist/meals/name/{name}")
    public ArrayList<Meal> getByName(@PathVariable String name) {
        String data = restTemplate.getForObject("https://www.themealdb.com/api/json/v1/1/search.php?s="+name, String.class);
        ArrayList<Meal> allMeals = mealService.getMeals(data);
        allMeals.addAll(this.mealService.getLocalMealsByName(name));

        return allMeals;
    }

    @GetMapping("api/recipionist/meals/categories")
    public ArrayList<MealCategory> getCategories() {
       String data = restTemplate.getForObject("https://www.themealdb.com/api/json/v1/1/categories.php", String.class);

        return mealCategoryService.getMealCategories(data);
    }

    @GetMapping("api/recipionist/meals/areas")
    public ArrayList<MealArea> getAreas() {
        String data = restTemplate.getForObject("https://www.themealdb.com/api/json/v1/1/list.php?a=list", String.class);

        return mealAreaService.getMealAreas(data);
    }


    @GetMapping("api/recipionist/meals/ingredient/{ingredient}")
    public ArrayList<ShortMeal> getByIngredient(@PathVariable String ingredient) {
        String data = restTemplate.getForObject("https://www.themealdb.com/api/json/v1/1/filter.php?i="+ingredient, String.class);
        ArrayList<ShortMeal> allMeals = mealService.getMealsShort(data);

        allMeals.addAll(this.mealService.getLocalMealsByIngredient(ingredient));

        return allMeals;
    }


    @GetMapping("api/recipionist/meals/category/{category}")
    public ArrayList<ShortMeal> getByCategory(@PathVariable String category) {
        String data = restTemplate.getForObject("https://www.themealdb.com/api/json/v1/1/filter.php?c="+category, String.class);
        ArrayList<ShortMeal> allMeals = mealService.getMealsShort(data);

        allMeals.addAll(this.mealService.getLocalMealsByCategory(category));
        return allMeals;
    }

    @GetMapping("api/recipionist/meals/area/{area}")
    public ArrayList<ShortMeal> getByArea(@PathVariable String area) {
        String data =  restTemplate.getForObject("https://www.themealdb.com/api/json/v1/1/filter.php?a="+area, String.class);

        ArrayList<ShortMeal> allMeals = mealService.getMealsShort(data);

        allMeals.addAll(this.mealService.getLocalMealsByArea(area));
        return allMeals;
    }

    @RequestMapping(value = "/api/recipionist/meals/new", method = RequestMethod.POST)
    @ResponseBody
    public Meal createMeal(
           Meal meal
            ) {
        Meal mealResult = mealService.saveMeal(meal);
        System.out.println(meal);
        return mealResult;
    }


    @RequestMapping(value = "/api/recipionist/meals/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Meal updateMeal(@PathVariable String id,
            Meal meal) {
        System.out.println(meal);
        return mealService.updateMeal(meal, id);
    }

    @RequestMapping(value = "/api/recipionist/meals/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteMeal( @PathVariable String id) {
        return mealService.deleteMeal(id);
    }


    @PatchMapping("/api/recipionist/meals/update/{id}")
    public Meal updateMealPatch(@PathVariable String id, Meal meal) {
        return mealService.updateMealPatch(meal, id);
    }
}
