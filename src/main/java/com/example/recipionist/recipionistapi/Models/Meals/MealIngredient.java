package com.example.recipionist.recipionistapi.Models.Meals;

import com.example.recipionist.recipionistapi.Models.Ingredient.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name ="meal_ingredients" )
public class MealIngredient {
    @Id
    @SequenceGenerator(
            name = "meal_ingredient_id_seq",
            sequenceName = "meal_ingredient_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "meal_ingredient_id_seq"
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
            name = "meal_id",
            referencedColumnName = "id"
    )
    private Meal meal;


    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        setMeal(meal, true);
    }

    public void setMeal(Meal meal, boolean add) {
        this.meal = meal;
        if (meal != null && add) {
            meal.addMealIngredient(this, false);
        }
    }


    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        setIngredient(ingredient, true);
    }
    public void setIngredient(Ingredient ingredient, boolean add) {
        this.ingredient = ingredient;
        if (ingredient != null && add) {
            ingredient.addMealIngredient(this, false);
        }
    }

    @Override
    public String toString() {
        return "MealIngredient{" +
                "id=" + id +
                ", measure='" + measure + '\'' +
                '}';
    }
}
