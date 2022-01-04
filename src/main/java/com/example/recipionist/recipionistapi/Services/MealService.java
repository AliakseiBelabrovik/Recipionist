package com.example.recipionist.recipionistapi.Services;

import com.example.recipionist.recipionistapi.DataLoader;
import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import com.example.recipionist.recipionistapi.Models.Meals.MealsObjects;
import com.example.recipionist.recipionistapi.Models.Meals.ShortMeal;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MealService {

    DataLoader dataLoader = new DataLoader();
    MealsObjects localMeals = new MealsObjects();


    public Meal getSingleMeal(String data){

        Meal meal;
        ArrayList<Meal> meals = this.getMeals(data);

        if(meals.size() == 1){
            meal = meals.get(0);
        }else{
            meal = null;
        }
        return meal;
    }

    public ArrayList<Meal> getLocalMealsByName(String name){
        ArrayList<Meal> locals = new ArrayList<>();
        for (Meal meal:
                this.localMeals.getAll()
        ) {
            meal = this.localMeals.getByName(name);
            if(meal != null){
                locals.add(meal);
            }
        }
        return locals;
    }

    public ArrayList<Meal> getLocalMealsByFirstletter(String firstletter){
        ArrayList<Meal> locals = new ArrayList<>();
        for (Meal meal:
                this.localMeals.getAll()
        ) {
            meal = this.localMeals.getByFirstletter(firstletter);
            if(meal != null){
                locals.add(meal);
            }
        }
        return locals;
    }

    public ArrayList<ShortMeal> getLocalMealsByIngredient(String ingredient){
        ArrayList<ShortMeal> locals = new ArrayList<>();
        for (Meal meal:
                this.localMeals.getAll()
        ) {
            meal = this.localMeals.getByIngredient(ingredient);
            if(meal != null){
                ShortMeal shortMeal = new ShortMeal(meal.getId().toString(), meal.getMealName(), meal.getThumbnail());
                locals.add(shortMeal);
            }
        }
        return locals;
    }

    public ArrayList<ShortMeal> getLocalMealsByCategory(String category){
        ArrayList<ShortMeal> locals = new ArrayList<>();
        for (Meal meal:
                this.localMeals.getAll()
        ) {
            meal = this.localMeals.getByCategory(category);
            if(meal != null){
                ShortMeal shortMeal = new ShortMeal(meal.getId().toString(), meal.getMealName(), meal.getThumbnail());
                locals.add(shortMeal);
            }
        }
        return locals;
    }

    public ArrayList<ShortMeal> getLocalMealsByArea(String area){
        ArrayList<ShortMeal> locals = new ArrayList<>();
        for (Meal meal:
                this.localMeals.getAll()
        ) {
            meal = this.localMeals.getByArea(area);
            if(meal != null){
                ShortMeal shortMeal = new ShortMeal(meal.getId().toString(), meal.getMealName(), meal.getThumbnail());
                locals.add(shortMeal);
            }
        }
        return locals;
    }

    public Meal getSingleMeal(String data, String id){
        Meal meal;
        if(id.contains("localMeal")){
            meal = this.localMeals.get(id);
        }else{
            ArrayList<Meal> meals = this.getMeals(data);
            if(meals.size() == 1){
                meal = meals.get(0);
            }else{
                meal= null;
            }
        }
        return meal;
    }

    public Meal saveMeal(Meal meal){
        meal = this.localMeals.put(meal);
        return meal;
    }

    public Meal updateMeal(Meal meal, String id){
        meal = this.localMeals.updateMeal(meal, id);
        return meal;
    }

    public Meal updateMealPatch(Meal meal, String id) {
        meal = this.localMeals.updateMealPatch(meal, id);
        return meal;
    }


    public boolean deleteMeal( String id){
        this.localMeals.delete(id);
        if(this.localMeals.get(id) == null){
            return true;
        }
        return false;
    }
    public ArrayList<Meal> getMeals(String data) {

        JSONObject mealObj = dataLoader.loadData(data);
        JSONArray jsonArray = (JSONArray) mealObj.remove("meals");

        ArrayList<Meal> mealsList = new ArrayList<>();

        while (jsonArray != null && !jsonArray.isEmpty()){

            JSONObject meal = (JSONObject) jsonArray.get(jsonArray.size()-1);
            jsonArray.remove(jsonArray.size()-1);


            ArrayList<String> ingredients = this.getManyFromJsonArray(meal, "strIngredient");
            ArrayList<String> measures = this.getManyFromJsonArray(meal, "strMeasure");

            Meal mealModel = new Meal(
                    (Long) meal.get("idMeal"),
                    (String) meal.get("strMeal"),
                    (String) meal.get("strDrinkAlternate"),
                    (String) meal.get("strCategory"),
                    (String) meal.get("strArea"),
                    (String) meal.get("strMealThumb"),
                    (String) meal.get("strInstructions"),
                    (String) meal.get("strTags"),
                    ingredients,
                    measures,
                    (String) meal.get("strImageSource")
            );
            mealsList.add(mealModel);

        }




        return mealsList;
    }

    public ArrayList<ShortMeal> getMealsShort(String data){
        JSONObject mealsShort = dataLoader.loadData(data);
        ArrayList<ShortMeal> foundMeals = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) mealsShort.remove("meals");
        while ( jsonArray != null && !jsonArray.isEmpty()){
            JSONObject meal = (JSONObject) jsonArray.get(jsonArray.size()-1);
            jsonArray.remove(jsonArray.size()-1);
            ShortMeal shortMeal = new ShortMeal(
                    (String) meal.get("idMeal"),
                    (String) meal.get("strMeal"),
                    (String) meal.get("strMealThumb")
            );
            foundMeals.add(shortMeal);

        }
        return foundMeals;
    }

    private ArrayList<String> getManyFromJsonArray(JSONObject meal, String similarElement){
        ArrayList<String> elements = new ArrayList<>();
        Pattern pattern= Pattern.compile(similarElement);

        Matcher matcher = pattern.matcher(meal.toString());
        int counterElements = 1;
        while (matcher.find()){

            if( meal.get((similarElement+counterElements)) != null && !meal.get((similarElement+counterElements)).equals("")){
                elements.add((String) meal.get((similarElement+counterElements)));
            }
            meal.remove((similarElement+counterElements));
            counterElements++;
        }

        return elements;

    }


}
