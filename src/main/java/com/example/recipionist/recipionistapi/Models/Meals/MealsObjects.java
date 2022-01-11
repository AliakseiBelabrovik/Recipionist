package com.example.recipionist.recipionistapi.Models.Meals;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Locale;

@Service
public class MealsObjects {

    protected ArrayList<Meal> mealsLocal;
    private final String identifierLocal = "localMeal";
    private int counter = 0;

    public MealsObjects(){
        mealsLocal = new ArrayList<Meal>();
    }

    public Meal put(Meal meal){
        counter++;
        meal.id = Long.parseLong(identifierLocal+counter);
        mealsLocal.add(meal);
        return meal;
    }

    public Meal get(String id){
        for (Meal meal:
             mealsLocal) {
            if(meal.id.equals(id)){
                return meal;
            }
        }
        return null;
    }

    public Meal getByName(String name){
        for (Meal meal:
                mealsLocal) {
            if(meal.mealName.equals(name)){
                return meal;
            }
        }
        return null;
    }


    public Meal getByFirstletter(String firstletter){
        for (Meal meal:
                mealsLocal) {
            if(meal.mealName.substring(0,1).toLowerCase(Locale.ROOT).equals(firstletter.toLowerCase(Locale.ROOT))){
                return meal;
            }
        }
        return null;
    }

    public Meal getByIngredient(String ingredient){
        for (Meal meal:
                mealsLocal) {
            for (String ing:
                    meal.getIngredients()) {
                if(ing.equals(ingredient) ){
                    return meal;
                }
            }
        }
        return null;
    }


    public Meal getByCategory(String category){
        for (Meal meal:
                mealsLocal) {
            if(meal.category.equals(category) ){
                return meal;
            }
        }
        return null;
    }

    public Meal getByArea(String area){
        for (Meal meal:
                mealsLocal) {
            if(meal.area.equals(area) ){
                return meal;
            }
        }
        return null;
    }

    public Meal delete(String id){
        for (Meal meal:
                mealsLocal) {
            if(meal.id.equals(id)){
                mealsLocal.remove(meal);
                return meal;
            }
        }
        return null;
    }

    public Meal updateMeal(Meal meal, String id){
        for (int i = 0; i < mealsLocal.size(); i++) {
            if (mealsLocal.get(i).id.equals(id)) {
                mealsLocal.set(i, meal);
                return mealsLocal.get(i);
            }
        }
        /*
        for (Meal meallocal:
                mealsLocal) {
            if(meallocal.id.equals(id)){
                meallocal = meal;
                return meallocal;
            }
        }

         */
        return null;
    }


    public Meal updateMealPatch(Meal meal, String id) {
        for (int i = 0; i < mealsLocal.size(); i++) {
            if (mealsLocal.get(i).id.equals(id)) {

                Meal actualMeal = mealsLocal.get(i);

                boolean needUpdate = false;

                if (StringUtils.hasLength(meal.getMealName())) {
                    actualMeal.setMealName(meal.getMealName());
                }
                if (StringUtils.hasLength(meal.getDrinkAlternate())) {
                    actualMeal.setDrinkAlternate(meal.getDrinkAlternate());
                }
                if (StringUtils.hasLength(meal.getCategory())) {
                    actualMeal.setCategory(meal.getCategory());
                }
                if (StringUtils.hasLength(meal.getArea())) {
                    actualMeal.setArea(meal.getArea());
                }
                if (StringUtils.hasLength(meal.getThumbnail())) {
                    actualMeal.setThumbnail(meal.getThumbnail());
                }
                if (StringUtils.hasLength(meal.getInstructions())) {
                    actualMeal.setInstructions(meal.getInstructions());
                }
                if (StringUtils.hasLength(meal.getTags())) {
                    actualMeal.setTags(meal.getTags());
                }
                if (meal.getIngredients() != null && meal.getIngredients().size() > 0) {
                    actualMeal.setIngredients(meal.getIngredients());
                }
                if (meal.getMeasures() != null && meal.getMeasures().size() > 0) {
                    actualMeal.setMeasures(meal.getMeasures());
                }
                if (StringUtils.hasLength(meal.getImageSrc())) {
                    actualMeal.setImageSrc(meal.getImageSrc());
                }
                return mealsLocal.get(i);
            }
        }
        return null;
    }




    public ArrayList<Meal> getAll(){
        return this.mealsLocal;
    }

}
