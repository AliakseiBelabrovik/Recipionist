package com.example.recipionist.recipionistapi.Models.Cocktail;


import com.example.recipionist.recipionistapi.Models.Ingredient.Ingredient;
import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name ="cocktail_ingredients" )
public class CocktailIngredient {

    @Id
    @SequenceGenerator(
            name = "cocktail_ingredient_id_seq",
            sequenceName = "cocktail_ingredient_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cocktail_ingredient_id_seq"
    )
    private Long id;

    /**
     * TO DO: ADD Relationships & Measure Attribute
     */
    private String measure;

    //@ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(
            name = "ingredient_id", //name of the column to create inside table meal_ingredient
            referencedColumnName = "id" //name of the variable it is referencing inside the ingredient class
    )
    private Ingredient ingredient;

    //@ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(
            name = "cocktail_id",
            referencedColumnName = "id"
    )
    private Cocktail cocktail;


    public Cocktail getCocktail() {
        return cocktail;
    }

    public void setCocktail(Cocktail cocktail) { this.cocktail = cocktail; }

//    public void setCocktail(Cocktail cocktail) {
//        setCocktail(cocktail, true);
//    }
//
//    public void setCocktail(Cocktail cocktail, boolean add) {
//        this.cocktail = cocktail;
//        if (cocktail != null && add) {
//            cocktail.addCocktailIngredient(this, false);
//        }
//    }


    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
       this.ingredient = ingredient;
    }

//    public void setIngredient(Ingredient ingredient) {
//        setIngredient(ingredient, true);
//    }
//    public void setIngredient(Ingredient ingredient, boolean add) {
//        this.ingredient = ingredient;
//        if (ingredient != null && add) {
//            ingredient.addCocktailIngredient(this, false);
//        }
//    }

    public Long getId() {
        return id;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    @Override
    public String toString() {
        return "CocktailIngredient{" +
                "id=" + id +
                ", measure='" + measure + '\'' +
                '}';
    }
}
