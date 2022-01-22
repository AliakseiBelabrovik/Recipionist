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

import javax.annotation.PostConstruct;
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

    /**
     * After the application runs, meal categories are got from the MealDB API and saved in the database
     * If a particular meal category does exist, the entry is not being updated
     */
    @PostConstruct
    public void generateMealCategories() {
        List<MealCategory> mealCategories = getCategories();
        for (MealCategory mealCategory : mealCategories) {
            mealCategoryService.addNewMealCategoryInDatabase(mealCategory);
        }
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

        //if meal is present in the local DB, get it
        if (mealService.isLocalMeal(Long.parseLong(id))) {
            try {
                return mealService.getMealById(Long.parseLong(id));
            } catch (IllegalStateException e) {
                System.out.println("Exception = " + e.getMessage());
                String data = restTemplate.getForObject("https://www.themealdb.com/api/json/v1/1/lookup.php?i="+id, String.class);
                return mealService.getSingleMeal(data, id);
            }
        }
        //otherwise ask the MealDB
        String data = restTemplate.getForObject("https://www.themealdb.com/api/json/v1/1/lookup.php?i="+id, String.class);
        return mealService.getSingleMeal(data, id);
    }

    @GetMapping("api/recipionist/meals/name/{name}")
    public ArrayList<Meal> getByName(@PathVariable String name) {
        String data = restTemplate.getForObject("https://www.themealdb.com/api/json/v1/1/search.php?s="+name, String.class);
        ArrayList<Meal> allMeals = mealService.getMeals(data); //Meals from MealDB
        allMeals.addAll(this.mealService.getLocalMealsByName(name)); //Meals from the local DB

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
        allMeals.addAll(this.mealService.getLocalMealsByIngredientName(ingredient));

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
        System.out.println(meal);
        //Meal mealResult = mealService.saveMeal(meal);
        Meal mealResult = mealService.addNewMealToDatabase(meal);
        System.out.println(mealResult);
        return mealResult;
        //return meal;
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
        //return mealService.deleteMeal(id);
        return mealService.deleteMealFromDatabase(Long.parseLong(id));
        //return false;
    }


    @PatchMapping("/api/recipionist/meals/update/{id}")
    public Meal updateMealPatch(@PathVariable String id, Meal meal) {
        return mealService.updateMealPatch(meal, Long.parseLong(id));
    }
}
