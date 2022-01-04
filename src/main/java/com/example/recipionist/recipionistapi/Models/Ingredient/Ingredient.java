package com.example.recipionist.recipionistapi.Models.Ingredient;

import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import com.example.recipionist.recipionistapi.Models.Meals.MealIngredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "ingredient",
        uniqueConstraints = @UniqueConstraint(
                name = "ingredient_name_unique",
                columnNames = "name"
        )
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ingredient {

    @Id
    @SequenceGenerator(
            name = "ingredient_ID_seq",
            sequenceName = "ingredient_ID_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ingredient_ID_seq"
    )
    @Column(name="id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String ingredientName;

    //TODO Add OneToMany Relationship with mappedBY attributes from MealIngredient and CocktailIngredient classes
    @OneToMany(
            mappedBy = "ingredient"
    )
    private List<MealIngredient> mealIngredients;

    public void addMealIngredient(MealIngredient mealIngredient) {
        if (mealIngredients == null) {
            mealIngredients = new ArrayList<>();
        }
        mealIngredients.add(mealIngredient);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }
}
