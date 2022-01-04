package com.example.recipionist.recipionistapi.Models.Cocktail;

import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Locale;

public class CocktailObjects {


        protected ArrayList<Cocktail> cocktailLocal;
        private final String identifierLocal = "localCocktail";
        private int counter = 0;

        public CocktailObjects(){
            cocktailLocal = new ArrayList<Cocktail>();
        }

        public Cocktail put(Cocktail cocktail){
            counter++;
            cocktail.id = identifierLocal+counter;
            cocktailLocal.add(cocktail);
            return cocktail;
        }

        public Cocktail get(String id){
            for (Cocktail cocktail:
                    cocktailLocal) {
                if(cocktail.id.equals(id)){
                    return cocktail;
                }
            }
            return null;
        }

        public Cocktail getByName(String name){
            for (Cocktail cocktail:
                    cocktailLocal) {
                if(cocktail.name.equals(name)){
                    return cocktail;
                }
            }
            return null;
        }


        public Cocktail getByFirstletter(String firstletter){
            for (Cocktail cocktail:
                    cocktailLocal) {
                if(cocktail.name.substring(0,1).toLowerCase(Locale.ROOT).equals(firstletter.toLowerCase(Locale.ROOT))){
                    return cocktail;
                }
            }
            return null;
        }

        public Cocktail getByIngredient(String ingredient){
            for (Cocktail cocktail:
                    cocktailLocal) {
                for (String ing:
                        cocktail.getIngredients()) {
                    if(ing.equals(ingredient) ){
                        return cocktail;
                    }
                }
            }
            return null;
        }

        public Cocktail getByGlass(String glass){
            for (Cocktail cocktail:
                    cocktailLocal) {
                if(cocktail.glass.equals(glass) ){
                    return cocktail;
                }
            }
            return null;
        }

        public Cocktail getByCategory(String category){
            for (Cocktail cocktail:
                    cocktailLocal) {
                if(cocktail.category.equals(category) ){
                    return cocktail;
                }
            }
            return null;
        }


        public Cocktail delete(String id){
            for (Cocktail cocktail:
                    cocktailLocal) {
                if(cocktail.id.equals(id)){
                    cocktailLocal.remove(cocktail);
                    return cocktail;
                }
            }
            return null;
        }

        public Cocktail updateCocktail(Cocktail cocktail, String id){
            for (int i = 0; i < cocktailLocal.size(); i++) {
                if (cocktailLocal.get(i).id.equals(id)) {
                    cocktailLocal.set(i, cocktail);
                    return cocktailLocal.get(i);
                }
            }

            return null;
        }


    public Cocktail updateCocktailPatch(Cocktail cocktail, String id){
        for (int i = 0; i < cocktailLocal.size(); i++) {
            if (cocktailLocal.get(i).id.equals(id)) {

                Cocktail actualCocktail = cocktailLocal.get(i);

                if (StringUtils.hasLength(cocktail.getName())) {
                    actualCocktail.setName(cocktail.getName());
                }
                if (StringUtils.hasLength(cocktail.getCategory())) {
                    actualCocktail.setCategory(cocktail.getCategory());
                }
                if (StringUtils.hasLength(cocktail.getAlcohol())) {
                    actualCocktail.setAlcohol(cocktail.getAlcohol());
                }
                if (StringUtils.hasLength(cocktail.getGlass())) {
                    actualCocktail.setGlass(cocktail.getGlass());
                }
                if (StringUtils.hasLength(cocktail.getInstructions())) {
                    actualCocktail.setInstructions(cocktail.getInstructions());
                }
                if (StringUtils.hasLength(cocktail.getThumbnail())) {
                    actualCocktail.setThumbnail(cocktail.getThumbnail());
                }
                if (cocktail.getIngredients() != null && cocktail.getIngredients().size() > 0) {
                    actualCocktail.setIngredients(cocktail.getIngredients());
                }
                if (cocktail.getMeasures() != null && cocktail.getMeasures().size() > 0) {
                    actualCocktail.setMeasures(cocktail.getMeasures());
                }

                return cocktailLocal.get(i);
            }
        }

        return null;
    }







        public ArrayList<Cocktail> getAll(){
            return this.cocktailLocal;
        }


}
