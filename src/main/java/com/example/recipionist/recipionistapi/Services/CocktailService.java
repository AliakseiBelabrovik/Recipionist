package com.example.recipionist.recipionistapi.Services;

import com.example.recipionist.recipionistapi.DataLoader;
import com.example.recipionist.recipionistapi.Models.Cocktail.*;
import com.example.recipionist.recipionistapi.Repositories.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CocktailService {


    DataLoader dataLoader = new DataLoader();
    CocktailObjects localCocktails = new CocktailObjects();
    CocktailRepository CocktailRepository;
    IngredientService ingredientService;
    CocktailCategoryService CocktailCategoryService;
    UserService userService;
    CocktailIngredientService CocktailIngredientService;

    public CocktailService(){
        
        localCocktails = new CocktailObjects();
    }


    public Cocktail getSingleCocktail(String data){
       Cocktail cocktail;
        ArrayList<Cocktail> cocktails = this.getCocktails(data);
        if(cocktails.size() == 1){
            cocktail = cocktails.get(0);
        }else{
            return null;
        }

        return cocktail;
    }

    public Cocktail getSingleCocktail(String data, String id){
        Cocktail cocktail;
        if(id.contains("localCocktail")){
            cocktail = this.localCocktails.get(id);
        }else{
            ArrayList<Cocktail> cocktails = this.getCocktails(data);
            if(cocktails.size() == 1){
                cocktail = cocktails.get(0);
            }else{
                cocktail= null;
            }
        }
        return cocktail;
    }

    public ArrayList<CocktailShort> getLocalCocktailsByName(String name){
        ArrayList<CocktailShort> locals = new ArrayList<>();
        for (Cocktail cocktail:
                this.localCocktails.getAll()
        ) {
            cocktail = this.localCocktails.getByName(name);
            if(cocktail != null){
               CocktailShort shortCocktail = new CocktailShort(cocktail.getId(), cocktail.getName(), cocktail.getThumbnail());
                locals.add(shortCocktail);
            }
        }
        return locals;
    }

    public ArrayList<CocktailShort> getLocalCocktailsByGlass(String glass){
        ArrayList<CocktailShort> locals = new ArrayList<>();
        for (Cocktail cocktail:
                this.localCocktails.getAll()
        ) {
            cocktail = this.localCocktails.getByGlass(glass);
            if(cocktail != null){
                CocktailShort shortCocktail = new CocktailShort(cocktail.getId(), cocktail.getName(), cocktail.getThumbnail());
                locals.add(shortCocktail);
            }
        }
        return locals;
    }

    public ArrayList<CocktailShort> getLocalCocktailsByCategory(String category){
        ArrayList<CocktailShort> locals = new ArrayList<>();
        for (Cocktail cocktail:
                this.localCocktails.getAll()
        ) {
            cocktail = this.localCocktails.getByCategory(category);
            if(cocktail != null){
                CocktailShort shortCocktail = new CocktailShort(cocktail.getId(), cocktail.getName(), cocktail.getThumbnail());
                locals.add(shortCocktail);
            }
        }
        return locals;
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
        ArrayList<CocktailShort> locals = new ArrayList<>();
        for (Cocktail cocktail:
                this.localCocktails.getAll()
        ) {
            cocktail = this.localCocktails.getByIngredient(ingredient);
            if(cocktail != null){
                CocktailShort cocktailShort = new CocktailShort(cocktail.getId(), cocktail.getName(), cocktail.getThumbnail());
                locals.add(cocktailShort);
            }
        }
        return locals;
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
                    (Long) cocktail.get("idDrink"),
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
        JSONObject cocktailsShort = dataLoader.loadData(data);
        ArrayList<CocktailShort> foundDrinks = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) cocktailsShort.remove("drinks");
        while (jsonArray != null && !jsonArray.isEmpty()){
            JSONObject drink = (JSONObject) jsonArray.get(jsonArray.size()-1);
            jsonArray.remove(jsonArray.size()-1);
            CocktailShort cocktailShort = new CocktailShort(
                    (Long) drink.get("idDrink"),
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
