package com.example.recipionist.recipionistapi.Services;

import com.example.recipionist.recipionistapi.DataLoader;
import com.example.recipionist.recipionistapi.Models.Cocktail.*;
import com.example.recipionist.recipionistapi.Models.Ingredient.Ingredient;
import com.example.recipionist.recipionistapi.Models.Meals.*;
import com.example.recipionist.recipionistapi.Models.User.User;
import com.example.recipionist.recipionistapi.Repositories.*;

import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CocktailService {


    DataLoader dataLoader = new DataLoader();
    CocktailObjects localCocktails = new CocktailObjects();
    CocktailRepository cocktailRepository;
    IngredientService ingredientService;
    CocktailCategoryService cocktailCategoryService;
    UserService userService;
    CocktailIngredientService cocktailIngredientService;



    // New database methods
    public ArrayList<Cocktail> getAllCocktails() {
        ArrayList<Cocktail> allLocalCocktails = new ArrayList<>(cocktailRepository.findAll());

        for (Cocktail localCocktail:
                allLocalCocktails) {
            localCocktail.setMeasures(cocktailIngredientService.getMeasures(localCocktail));
            localCocktail.setIngredients(cocktailIngredientService.getCocktailIngredientsAsListOfStrings(localCocktail));
            localCocktail.setCategory(localCocktail.getCocktailCategory().getCategory());
        }
        return allLocalCocktails;
    }

    public Cocktail getCocktailById(Long cocktailId) {
        Cocktail localCocktail = cocktailRepository.findById(cocktailId)
                .orElseThrow(() -> new IllegalStateException("There is no meal with the id " + cocktailId));

        //set list of measures & ingredient names to be sent to the front end
        localCocktail.setMeasures(cocktailIngredientService.getMeasures(localCocktail));
        localCocktail.setIngredients(cocktailIngredientService.getCocktailIngredientsAsListOfStrings(localCocktail));

        localCocktail.setCategory(localCocktail.getCocktailCategory().getCategory());

        return localCocktail;
    }


    public Cocktail updateCocktailPatch(Cocktail cocktail, Long id) {

        try {
            Cocktail currentCocktailInDB = getCocktailById(id);
            boolean needUpdate = false;

            if (StringUtils.hasLength(cocktail.getName())) {
                currentCocktailInDB.setName(cocktail.getName());
                //maybe mealRepository.updateMealNameById(meal.getMealName(), id);
            }
            if (StringUtils.hasLength(cocktail.getAlcohol())) {
                currentCocktailInDB.setAlcohol(cocktail.getAlcohol());
                //maybe mealRepository.updateAreaById(meal.getArea(), id);
            }
            if (StringUtils.hasLength(cocktail.getThumbnail())) {
                currentCocktailInDB.setThumbnail(cocktail.getThumbnail());
            }

            if (StringUtils.hasLength(cocktail.getInstructions())) {
                currentCocktailInDB.setInstructions(cocktail.getInstructions());
            }
            if (StringUtils.hasLength(cocktail.getGlass())) {
                currentCocktailInDB.setGlass(cocktail.getGlass());
            }


            if (StringUtils.hasLength(cocktail.getCategory())) {
                CocktailCategory cocktailCategory;
                try {
                    cocktailCategory = cocktailCategoryService.getCocktailCategoryByName(cocktail.getCategory());

                } catch (IllegalStateException exception) {
                    cocktailCategory = CocktailCategory.builder()
                            .category(cocktail.getCategory())
                            .build();
                    cocktailCategoryService.addNewCocktailCategoryInDatabase(cocktailCategory);
                    cocktailCategory = cocktailCategoryService.getCocktailCategoryByName(cocktail.getCategory());
                }
                currentCocktailInDB.setCategory(cocktail.getCategory()); //updates string
                currentCocktailInDB.setCocktailCategory(cocktailCategory); //updates the table (foreign key)
            }

            //If there is any ingredient and measure
            if (cocktail.getIngredients() != null && cocktail.getIngredients().size() > 0
                    && cocktail.getMeasures() != null && cocktail.getMeasures().size() > 0
            ) {

                //get the list of meal ingredients related to the current recipe
                List<CocktailIngredient> currentCocktailIngredientsList = cocktailIngredientService.findCocktailIngredientsByCocktail(currentCocktailInDB);

                //for each ingredient
                for (int i = 0; i < cocktail.getIngredients().size(); i++) {

                    boolean ingredientExists = false;
                    int position = -1;

                    //look in the list of current ingredients, if the ingredient for this recipe is already there
                    for (int y = 0; y < currentCocktailIngredientsList.size(); y++) {
                        if (cocktail.getIngredients().get(i).equals(currentCocktailIngredientsList.get(y).getIngredient().getIngredientName())) {
                            ingredientExists = true;
                            position = y;
                        }
                    }
                    if (ingredientExists) {
                        CocktailIngredient currentCocktailIngredient = currentCocktailIngredientsList.get(position);
                        //update its measure
                        currentCocktailIngredient.setMeasure(cocktail.getMeasures().get(i));
                        cocktailIngredientService.updateMeasureOfCocktailIngredient(
                                currentCocktailIngredient.getId(), cocktail.getMeasures().get(i));
                    } else {
                        //get ingredient from the database or else create & save it
                        Ingredient newIngredient = ingredientService.createIngredient(cocktail.getIngredients().get(i));

                        try {
                            ingredientService.addNewIngredientToDatabase(newIngredient);
                        } catch (IllegalStateException exception) {
                            newIngredient = ingredientService.getIngredientFromDatabaseByName(newIngredient.getIngredientName());
                        }

                        //creation of the cocktail ingredient
                        CocktailIngredient cocktailIngredient = cocktailIngredientService.createCocktailIngredient(cocktail.getMeasures().get(i), newIngredient, currentCocktailInDB);
                        cocktailIngredientService.addNewCocktailIngredientToDatabase(cocktailIngredient);
                    }
                }
            }
            return currentCocktailInDB;
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }


    public boolean deleteCocktailFromDatabase(Long id) {
        if (cocktailRepository.findById(id).isEmpty()) {
            throw new IllegalStateException("There is no meal with id " + id);
        }

        Cocktail cocktail = cocktailRepository.findById(id).get();
        System.out.println("Cocktail found before deleting: " + cocktail);

        //delete foreign key to the cocktail category
        cocktail.setCocktailCategory(null);

        //delete CocktailIngredients related to this cocktail
        cocktailIngredientService.deleteCocktailIngredientsRelatedToCocktail(cocktail);

        //delete foreign key to the user
        cocktail.setUser(null);


        cocktailRepository.deleteById(id);

        if (cocktailRepository.findById(id).isEmpty()) {
            System.out.println("The cocktail was deleted");
            return true;
        }
        System.out.println("The cocktail WAS NOT deleted");
        return false;
    }


    public Cocktail addNewCocktailToDatabase(Cocktail cocktail) {

        //check fist if cocktail is already present
        // TODO: check not only by name, but also by user, because
        //TODO: many users can have their own recipes with the same name
        Optional<Cocktail> optionalCocktail = cocktailRepository.findByCocktailName(cocktail.getName());
        if (optionalCocktail.isPresent()) {
            System.out.println("optionalCocktail = " + optionalCocktail + " is already present in the database.");
            return optionalCocktail.get();
        }
        //otherwise


        //find or save new mealCategory
        CocktailCategory cocktailCategory;
        try {
            cocktailCategory = cocktailCategoryService.getCocktailCategoryByName(cocktail.getCategory());
            //if there is no such cocktailCategory, create and save a new one
        } catch (IllegalStateException exception) {
            cocktailCategory = CocktailCategory.builder()
                    .category(cocktail.getCategory())
                    .build();
            cocktailCategoryService.addNewCocktailCategoryInDatabase(cocktailCategory);
            cocktailCategory = cocktailCategoryService.getCocktailCategoryByName(cocktail.getCategory());
        }

        cocktail.setCocktailCategory(cocktailCategory);
        cocktail.setCategory(cocktailCategory.getCategory());

        //get current user
        User currentUser = userService.getCurrentUser();
        cocktail.setUser(currentUser);

        List<CocktailIngredient> cocktailIngredientList = new ArrayList<>();

        //save Ingredient and then cocktailIngredient first
        for (int i= 0; i < cocktail.getIngredients().size(); i++) {
            Ingredient ingredient = ingredientService.createIngredient(cocktail.getIngredients().get(i));
            try {
                ingredientService.addNewIngredientToDatabase(ingredient);
            } catch (IllegalStateException exception) {
                ingredient = ingredientService.getIngredientFromDatabaseByName(ingredient.getIngredientName());
            }

            CocktailIngredient cocktailIngredient = cocktailIngredientService
                    .createCocktailIngredient(cocktail.getMeasures().get(i), ingredient, null);
            cocktailIngredientService.addNewCocktailIngredientToDatabase(cocktailIngredient);
            cocktailIngredientList.add(cocktailIngredient);
        }

        cocktailRepository.save(cocktail);
        for (int i = 0; i < cocktailIngredientList.size(); i++) {
            cocktailIngredientList.get(i).setCocktail(cocktail);
            cocktailIngredientService.updateCocktailOfCocktailIngredient(cocktailIngredientList.get(i).getId(), cocktail);
        }

        return cocktail;
    }

    private List<Cocktail> findByCocktailIngredient(List<CocktailIngredient> cocktailIngredientList) {
        return cocktailIngredientService.getCocktailByCocktailIngredient(cocktailIngredientList);
    }

    private ArrayList<CocktailShort> getShortCocktailFromCocktails(ArrayList<Cocktail> cocktails) {
        ArrayList<CocktailShort> shortCocktails = new ArrayList<>();
        for (Cocktail cocktail: cocktails
        ) {
            CocktailShort cocktailShort = CocktailShort.builder()
                    .id(cocktail.getId())
                    .cocktailName(cocktail.getName())
                    .thumbnail(cocktail.getThumbnail())
                    .build();
            shortCocktails.add(cocktailShort);
        }
        return shortCocktails;
    }
    // end of database methods


    public Cocktail getSingleCocktail(String data){
        Cocktail cocktail;
        ArrayList<Cocktail> cocktails = this.getCocktails(data);

        if(cocktails.size() == 1){
            cocktail = cocktails.get(0);
        }else{
            cocktail = null;
        }
        return cocktail;
    }

    public boolean isLocalCocktail(Long id) {
        return cocktailRepository.findById(id).isPresent();
    }

    public ArrayList<CocktailShort> getLocalCocktailsByName(String name){

        ArrayList<Cocktail> cocktailList =  cocktailRepository.findByCocktailNameContainingIgnoreCase(name);

        return getShortCocktailFromCocktails(cocktailList);

//        ArrayList<CocktailShort> locals = new ArrayList<>();
//        for (Cocktail cocktail:
//                this.localCocktails.getAll()
//        ) {
//            cocktail = this.localCocktails.getByName(name);
//            if(cocktail != null){
//               CocktailShort shortCocktail = new CocktailShort(cocktail.getId(), cocktail.getName(), cocktail.getThumbnail());
//                locals.add(shortCocktail);
//            }
//        }
//        return locals;
    }

    public ArrayList<CocktailShort> getLocalCocktailsByGlass(String glass){
        return getShortCocktailFromCocktails(cocktailRepository.findByGlassContainingIgnoreCase(glass));
    }

    public ArrayList<Cocktail> findCocktailsByCategory(CocktailCategory cocktailCategory) {
        return cocktailRepository.findAllByCocktailCategory(cocktailCategory);
    }

    public ArrayList<CocktailShort> getLocalCocktailsByCategory(String category){
        try {
           CocktailCategory cocktailCategory = cocktailCategoryService.getCocktailCategoryByName(category);
            return getShortCocktailFromCocktails(findCocktailsByCategory(cocktailCategory));
        } catch (IllegalStateException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
            return new ArrayList<CocktailShort>();
        }
    }

    public Cocktail saveCocktail(Cocktail cocktail){
        cocktail = this.localCocktails.put(cocktail);
        return cocktail;
    }

    public Cocktail updateCocktail(Cocktail cocktail, String id){
        cocktail = this.localCocktails.updateCocktail(cocktail, id);
        return cocktail;
    }

    public Cocktail updateCocktailPatch(Cocktail cocktail, String id) {
        cocktail = this.localCocktails.updateCocktailPatch(cocktail, id);
        return cocktail;
    }

    public boolean deleteCocktail( String id){
        this.localCocktails.delete(id);
        if(this.localCocktails.get(id) == null){
            return true;
        }
        return false;
    }

    public ArrayList<CocktailShort> getLocalCocktailsByIngredient(String ingredient){

        Ingredient ingredientLocal;
        try {
            ingredientLocal = ingredientService.getIngredientFromDatabaseByName(ingredient);
            List<CocktailIngredient> cocktailIngredients = cocktailIngredientService.findCocktailIngredientsByIngredient(ingredientLocal);
            ArrayList<Cocktail> cocktails = new ArrayList<>(findByCocktailIngredient(cocktailIngredients));
            return getShortCocktailFromCocktails(cocktails);
        } catch (IllegalStateException e) { //if no such an ingredient exists
            System.out.println(e.getMessage());
            return new ArrayList<CocktailShort>();
        }
    }

    public ArrayList<Cocktail> getLocalCocktailsByFirstletter(String firstletter){
        ArrayList<Cocktail> locals = new ArrayList<>();
        for (Cocktail cocktail : this.localCocktails.getAll()) {
            cocktail = this.localCocktails.getByFirstletter(firstletter);
            if(cocktail != null){
                locals.add(cocktail);
            }
        }
        return locals;
    }

    public ArrayList<Cocktail> getCocktails(String data) {
        JSONObject cocktailObj = dataLoader.loadData(data);
        JSONArray jsonArray = (JSONArray) cocktailObj.remove("drinks");

        ArrayList<Cocktail> cocktailList = new ArrayList<>();

        while (jsonArray != null && !jsonArray.isEmpty()){

            JSONObject cocktail = (JSONObject) jsonArray.get(jsonArray.size()-1);
            jsonArray.remove(jsonArray.size()-1);


            ArrayList<String> ingredients = this.getManyFromJsonArray(cocktail, "strIngredient");
            ArrayList<String> measures = this.getManyFromJsonArray(cocktail, "strMeasure");

            Cocktail cocktailModel = new Cocktail(
                    Long.parseLong((String)cocktail.get("idDrink")),
                    (String) cocktail.get("strDrink"),
                    (String) cocktail.get("strCategory"),
                    (String) cocktail.get("strAlcoholic"),
                    (String) cocktail.get("strGlass"),
                    (String) cocktail.get("strInstructions"),
                    (String) cocktail.get("strDrinkThumb"),
                    ingredients,
                    measures
            );
            cocktailList.add(cocktailModel);

        }


        return cocktailList;
    }

    public ArrayList<CocktailShort> getCocktailsShort(String data){
        if(data == null){
            return new ArrayList<CocktailShort>();
        }
        JSONObject cocktailsShort = dataLoader.loadData(data);
        ArrayList<CocktailShort> foundDrinks = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) cocktailsShort.remove("drinks");
        while (jsonArray != null && !jsonArray.isEmpty()){
            JSONObject drink = (JSONObject) jsonArray.get(jsonArray.size()-1);
            jsonArray.remove(jsonArray.size()-1);
            CocktailShort cocktailShort = new CocktailShort(
                    (Long.parseLong((String) drink.get("idDrink"))) ,
                    (String) drink.get("strDrink"),
                    (String) drink.get("strDrinkThumb")
            );
            foundDrinks.add(cocktailShort);

        }
        //System.out.println(foundDrinks.get(1).getCocktailName());
        return foundDrinks;
    }


    public ArrayList<CocktailCategory> getCocktailCategories (String data){
        JSONObject cocktailsShort = dataLoader.loadData(data);
        ArrayList<CocktailCategory> foundDrinks = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) cocktailsShort.remove("drinks");
        while (!jsonArray.isEmpty()){
            JSONObject drink = (JSONObject) jsonArray.get(jsonArray.size()-1);
            jsonArray.remove(jsonArray.size()-1);
            CocktailCategory cocktailCategory = new CocktailCategory(
                    (String) drink.get("strCategory")
            );
            foundDrinks.add(cocktailCategory);

        }
        //System.out.println(foundDrinks.get(1).getCocktailName());
        return foundDrinks;
    }


    public ArrayList<CocktailGlass> getLocalCocktailGlasses(ArrayList<CocktailGlass> cocktailGlasses) {

        //get all area names that we have in the local database
        ArrayList<String> glassNames = this.getAllGlassesOfAllLocalCocktails(this.getAllCocktails());
        ArrayList<CocktailGlass> onlyLocalGlasses = new ArrayList<>();
        for (String glassName:
                glassNames) {
            CocktailGlass temporaryGlass = CocktailGlass.builder()
                    .glass(glassName)
                    .build();
            System.out.println("glassName = " + glassName);
            boolean exists = false;
            for (CocktailGlass cocktailGlass:
                    cocktailGlasses) {
                if (glassName.equals(cocktailGlass.getGlass())) {
                    exists = true;
                }
            }
            //if the local meal area is not present in the reply of the MealDB, add it to the list of meal areas
            if (!exists) {
                onlyLocalGlasses.add(temporaryGlass);
            }
        }
        return onlyLocalGlasses;
    }

    private ArrayList<String> getAllGlassesOfAllLocalCocktails(ArrayList<Cocktail> allCocktails) {
        return getAllCocktails()
                .stream()
                .map(Cocktail::getGlass)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));

    }

    public ArrayList<CocktailGlass> getCocktailGlasses (String data){
        JSONObject cocktailsShort = dataLoader.loadData(data);
        ArrayList<CocktailGlass> foundDrinks = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) cocktailsShort.remove("drinks");
        while (!jsonArray.isEmpty()){
            JSONObject drink = (JSONObject) jsonArray.get(jsonArray.size()-1);
            jsonArray.remove(jsonArray.size()-1);
            CocktailGlass cocktailCategory = new CocktailGlass(
                    (String) drink.get("strGlass")
            );
            foundDrinks.add(cocktailCategory);

        }
        //System.out.println(foundDrinks.get(1).getCocktailName());
        return foundDrinks;
    }



    private ArrayList<String> getManyFromJsonArray(JSONObject Cocktail, String similarElement){
        ArrayList<String> elements = new ArrayList<>();
        Pattern pattern= Pattern.compile(similarElement);

        Matcher matcher = pattern.matcher(Cocktail.toString());
        int counterElements = 1;
        while (matcher.find()){

            if( Cocktail.get((similarElement+counterElements)) != null && !Cocktail.get((similarElement+counterElements)).equals("")){
                elements.add((String) Cocktail.get((similarElement+counterElements)));
            }
            Cocktail.remove((similarElement+counterElements));
            counterElements++;
        }

        return elements;

    }
}
