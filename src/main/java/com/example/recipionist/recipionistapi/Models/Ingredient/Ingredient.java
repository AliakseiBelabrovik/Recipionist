package com.example.recipionist.recipionistapi.Models.Ingredient;

import com.example.recipionist.recipionistapi.Models.Cocktail.CocktailIngredient;
import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import com.example.recipionist.recipionistapi.Models.Meals.MealIngredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    @Fetch(FetchMode.JOIN)
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "ingredient"
    )
    private List<MealIngredient> mealIngredients;

    @Fetch(FetchMode.JOIN)
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "ingredient"
    )
    private List<CocktailIngredient> cocktailIngredients;

    public void addMealIngredient(MealIngredient mealIngredient) {
        addMealIngredient(mealIngredient, true);
    }

    public void addCocktailIngredient(CocktailIngredient cocktailIngredient) {
        addCocktailIngredient(cocktailIngredient, true);
    }


    public void addMealIngredient(MealIngredient mealIngredient, boolean set) {
        if (mealIngredients == null) {
            mealIngredients = new ArrayList<>();
        }
        if (mealIngredient != null) {
            if (this.getMealIngredients().contains(mealIngredient)) {
                this.getMealIngredients().set(this.getMealIngredients().indexOf(mealIngredient), mealIngredient);
            } else {
                this.getMealIngredients().add(mealIngredient);
            }
            if (set) {
                mealIngredient.setIngredient(this, false);
            }
        }
    }

    public void addCocktailIngredient(CocktailIngredient cocktailIngredient, boolean set) {
        if (cocktailIngredients == null) {
            cocktailIngredients = new ArrayList<>();
        }
        if (cocktailIngredient != null) {
            if (this.getCocktailIngredients().contains(cocktailIngredient)) {
                this.getCocktailIngredients().set(this.getCocktailIngredients().indexOf(cocktailIngredient), cocktailIngredient);
            } else {
                this.getCocktailIngredients().add(cocktailIngredient);
            }
            if (set) {
                cocktailIngredient.setIngredient(this, false);
            }
        }
    }

    public void removeIngredient(MealIngredient mealIngredient) {
        this.getMealIngredients().remove(mealIngredient);
        mealIngredient.setIngredient(null);
    }


    public List<MealIngredient> getMealIngredients() {
        return mealIngredients;
    }


    public List<CocktailIngredient> getCocktailIngredients() {
        return cocktailIngredients;
    }

    public void setMealIngredients(List<MealIngredient> mealIngredients) {
        this.mealIngredients = mealIngredients;
    }

    public void setCocktailIngredients(List<CocktailIngredient> cocktailIngredients) {
        this.cocktailIngredients = cocktailIngredients;
    }



    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", ingredientName='" + ingredientName + '\'' +
                '}';
    }
}
