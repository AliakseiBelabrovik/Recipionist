package com.example.recipionist.recipionistapi.Models.Meals;

import com.example.recipionist.recipionistapi.Models.Ingredient.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "ingredient_id", //name of the column to create inside table meal_ingredient
            referencedColumnName = "id" //name of the variable it is referencing inside the ingredient class
    )
    private Ingredient ingredient;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "meal_id",
            referencedColumnName = "id"
    )
    private Meal meal;



}
