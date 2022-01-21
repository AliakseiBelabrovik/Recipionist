package com.example.recipionist.recipionistapi.Services;

import com.example.recipionist.recipionistapi.DataLoader;
import com.example.recipionist.recipionistapi.Models.Ingredient.Ingredient;
import com.example.recipionist.recipionistapi.Models.Meals.*;
import com.example.recipionist.recipionistapi.Models.User.User;
import com.example.recipionist.recipionistapi.Repositories.MealRepository;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class MealService {

    DataLoader dataLoader = new DataLoader();
    MealsObjects localMeals = new MealsObjects();
    MealRepository mealRepository;
    IngredientService ingredientService;
    UserService userService;
    MealIngredientService mealIngredientService;
    MealCategoryService mealCategoryService;


    public Meal getMealById(Long mealId) {
        return mealRepository.findById(mealId)
                .orElseThrow(() -> new IllegalStateException("There is no meal with the id " + mealId));
    }

    /**
     * Section with new methods to work with the database
     */


    public Meal updateMealPatch(Meal meal, Long id) {

        try {
            Meal currentMealInDB = getMealById(id);
            boolean needUpdate = false;

            if (StringUtils.hasLength(meal.getMealName())) {
                currentMealInDB.setMealName(meal.getMealName());
                //maybe mealRepository.updateMealNameById(meal.getMealName(), id);
            }
            if (StringUtils.hasLength(meal.getArea())) {
                currentMealInDB.setArea(meal.getArea());
                //maybe mealRepository.updateAreaById(meal.getArea(), id);
            }
            if (StringUtils.hasLength(meal.getThumbnail())) {
                currentMealInDB.setThumbnail(meal.getThumbnail());
            }
            if (StringUtils.hasLength(meal.getInstructions())) {
                currentMealInDB.setInstructions(meal.getInstructions());
            }
            if (StringUtils.hasLength(meal.getYoutubeLink())) {
                currentMealInDB.setYoutubeLink(meal.getYoutubeLink());
            }
            if (StringUtils.hasLength(meal.getTags())) {
                currentMealInDB.setTags(meal.getTags());
            }

            if (StringUtils.hasLength(meal.getCategory())) {
                MealCategory mealCategory;
                try { //get meal category from the database
                    mealCategory = mealCategoryService.getMealCategoryByName(meal.getCategory());
                    //if there is no such mealCategory, create and save a new one
                } catch (IllegalStateException exception) {
                    mealCategory = MealCategory.builder()
                            .categoryName(meal.getCategory())
                            .build();
                    mealCategoryService.addNewMealCategoryInDatabase(mealCategory);
                    mealCategory = mealCategoryService.getMealCategoryByName(meal.getCategory());
                }
                currentMealInDB.setCategory(meal.getCategory()); //updates string
                currentMealInDB.setMealCategory(mealCategory); //updates the table (foreign key) with mealCategory Object
            }

            //If there is any ingredient and measure
            if (meal.getIngredients() != null && meal.getIngredients().size() > 0
                && meal.getMeasures() != null && meal.getMeasures().size() > 0
                ) {

                //get the list of meal ingredients related to the current meal recipe
                List<MealIngredient> currentMealIngredientsList = mealIngredientService.findMealIngredientsByMeal(currentMealInDB);

                //for each ingredient
                for (int i = 0; i < meal.getIngredients().size(); i++) {

                    boolean ingredientExists = false;
                    int position = -1;
                    //look in the list of current meal ingredients, if the ingredient for this recipe is already there
                    for (int y = 0; y < currentMealIngredientsList.size(); y++) {
                        if (meal.getIngredients().get(i).equals(currentMealIngredientsList.get(y).getIngredient().getIngredientName())) {
                            ingredientExists = true;
                            position = y;
                        }
                    }
                    if (ingredientExists) {
                        MealIngredient currentMealIngredient = currentMealIngredientsList.get(position);
                        //update its measure
                        currentMealIngredient.setMeasure(meal.getMeasures().get(i));
                        mealIngredientService.updateMeasureOfMealIngredient(
                                currentMealIngredient.getId(), meal.getMeasures().get(i));
                    } else {
                        //get ingredient from the database or else create & save it
                        Ingredient newIngredient = Ingredient.builder()
                                .ingredientName(meal.getIngredients().get(i))
                                .build();

                        try {
                            ingredientService.addNewIngredientToDatabase(newIngredient);
                        } catch (IllegalStateException exception) {
                            newIngredient = ingredientService.getIngredientFromDatabaseByName(newIngredient.getIngredientName());
                        }

                        //creation of the meal ingredient
                        MealIngredient mealIngredient = MealIngredient.builder()
                                .measure(meal.getMeasures().get(i))
                                .ingredient(newIngredient)
                                .meal(currentMealInDB)
                                .build();
                        mealIngredientService.addNewMealIngredientToDatabase(mealIngredient);
                    }


                    /*

                    //find if there is already a meal ingredient with the same ingredient name
                    for (int y = 0; y < currentMealIngredientsList.size(); y++) {
                        if (currentMealIngredientsList.get(y).getIngredient().getIngredientName()
                                .equals(meal.getIngredients().get(i))) {
                            MealIngredient currentMealIngredient = currentMealIngredientsList.get(y);
                            //update its measure
                            currentMealIngredient.setMeasure(meal.getMeasures().get(i));
                            mealIngredientService.updateMeasureOfMealIngredient(
                                    currentMealIngredient.getId(), meal.getMeasures().get(i));
                            //otherwise, create meal ingredient
                        } else {
                            //get ingredient from the database or else create & save it
                            Ingredient newIngredient = Ingredient.builder()
                                    .ingredientName(meal.getIngredients().get(i))
                                    .build();

                            try {
                                ingredientService.addNewIngredientToDatabase(newIngredient);
                            } catch (IllegalStateException exception) {
                                newIngredient = ingredientService.getIngredientFromDatabaseByName(newIngredient.getIngredientName());
                            }

                            //creation of the meal ingredient
                            MealIngredient mealIngredient = MealIngredient.builder()
                                    .measure(meal.getMeasures().get(i))
                                    .ingredient(newIngredient)
                                    .meal(currentMealInDB)
                                    .build();
                            mealIngredientService.addNewMealIngredientToDatabase(mealIngredient);
                        }


                    }
                 */
                }
            }
            return currentMealInDB;
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public boolean deleteMealFromDatabase(Long id) {
        if (mealRepository.findById(id).isEmpty()) {
           throw new IllegalStateException("There is no meal with id " + id);
        }

        Meal meal = mealRepository.findById(id).get();
        System.out.println("Meal found before deleting: " + meal);

        //delete foreign key to the meal category
        meal.setMealCategory(null);

        //delete MealIngredients related to this meal
        mealIngredientService.deleteMealIngredientsRelatedToMeal(meal);

        //delete foreign key to the user
        meal.setUser(null);


        mealRepository.deleteById(id);

        if (mealRepository.findById(id).isEmpty()) {
            System.out.println("The meal was deleted");
            return true;
        }
        System.out.println("The meal WAS NOT deleted");
        return false;
    }




    public Meal addNewMealToDatabase(Meal meal) {

        //check fist if meal is already present
        // TODO: check not only by name, but also by user, because
        //TODO: many users can have their own recipes with the same name
        Optional<Meal> optionalMeal = mealRepository.findByMealName(meal.getMealName());
        if (optionalMeal.isPresent()) {
            System.out.println("optionalMeal = " + optionalMeal + " is already present in the database.");
            return optionalMeal.get();
        }
        //otherwise


        //then save mealCategory
        MealCategory mealCategory;
        try {
            mealCategory = mealCategoryService.getMealCategoryByName(meal.getCategory());
            //if there is no such mealCategory, create and save a new one
        } catch (IllegalStateException exception) {
            mealCategory = MealCategory.builder()
                    .categoryName(meal.getCategory())
                    .build();
            mealCategoryService.addNewMealCategoryInDatabase(mealCategory);
            mealCategory = mealCategoryService.getMealCategoryByName(meal.getCategory());
        }
        //TODO: do not forget to add meal to list in mealcategories
        //TODO: do not forget to add mealCategory to the meal
        meal.setMealCategory(mealCategory);

        //get current user
        User currentUser = userService.getCurrentUser();
        meal.setUser(currentUser);

        //TODO: do not forget to add meal to user

        List<MealIngredient> mealIngredientList = new ArrayList<>();

        //save Ingredient and then mealIngredient first
        for (int i= 0; i < meal.getIngredients().size(); i++) {
            Ingredient ingredient = Ingredient.builder()
                    .ingredientName(meal.getIngredients().get(i))
                    .build();
            try {
                ingredientService.addNewIngredientToDatabase(ingredient);
            } catch (IllegalStateException exception) {
                ingredient = ingredientService.getIngredientFromDatabaseByName(ingredient.getIngredientName());
            }

            //TODO: do not forget to add mealIngredient later

            MealIngredient mealIngredient = MealIngredient.builder()
                    .measure(meal.getMeasures().get(i))
                    .ingredient(ingredient)
                    //.ingredient(ingredientService.getIngredientFromDatabaseByName(meal.getIngredients().get(i)))
                    .build();
            mealIngredientService.addNewMealIngredientToDatabase(mealIngredient);

            mealIngredientList.add(mealIngredient);
        }

        mealRepository.save(meal);
        for (int i = 0; i < mealIngredientList.size(); i++) {
            mealIngredientList.get(i).setMeal(meal);
            mealIngredientService.updateMealOfMealIngredient(mealIngredientList.get(i).getId(), meal);
        }

        return meal;
    }

    /**
     * Section with methods for database ends here
     */




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

    /*
    public Meal updateMealPatch(Meal meal, String id) {
        meal = this.localMeals.updateMealPatch(meal, id);
        return meal;
    }

     */


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
