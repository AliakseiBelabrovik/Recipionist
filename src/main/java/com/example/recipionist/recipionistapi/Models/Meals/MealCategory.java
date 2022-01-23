package com.example.recipionist.recipionistapi.Models.Meals;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@XmlRootElement
@Entity
@Table(
        name = "meal_category",
        uniqueConstraints = @UniqueConstraint(
                name = "meal_category_name_unique",
                columnNames = "name"
        )
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealCategory{

    @Id
    @SequenceGenerator(
            name="meal_category_id_seq",
            sequenceName = "meal_category_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "meal_category_id_seq"
    )
    @Column(name = "id", updatable = false)
    protected Long id;

    @Column(name = "name", columnDefinition = "TEXT")
    protected String categoryName;

    @Column(name = "thumbnail", columnDefinition = "TEXT")
    protected String thumbnail;

    @Column(name = "description", columnDefinition = "TEXT")
    protected String description;

    @Column(name = "area", columnDefinition = "TEXT")
    protected String strArea;

    /*
    @Fetch(FetchMode.JOIN)
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "mealCategory")
    @JsonIgnore
    private List<Meal> meals;
    */


    public MealCategory(String category, String thumbnail, String description, String strArea) {
        this.categoryName = category;
        this.thumbnail = thumbnail;
        this.description = description;
        this.strArea = strArea;
    }


    /*
    public void addMeal(Meal meal) {
        addMeal(meal, true);
    }

    public void addMeal(Meal meal, boolean set) {
        if (meals == null) {
            meals = new ArrayList<>();
        }
        if (meals != null) {
            if (this.getMeals().contains(meal)) {
                this.getMeals().set(this.getMeals().indexOf(meal), meal);
            } else {
                this.getMeals().add(meal);
            }
            if (set) {
                meal.setMealCategory(this, false);
            }
        }
    }
    public void removeMeal(Meal meal) {
        this.getMeals().remove(meal);
        meal.setMealCategory(null);
    }

    @JsonManagedReference
    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

     */



    public void setCategory(String category) {
        this.categoryName = category;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    public Long getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategory() {
        return categoryName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public String getStrArea() {
        return strArea;
    }

    @Override
    public String toString() {
        return "MealCategory{" +
                "id='" + id + '\'' +
                ", category='" + categoryName + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", description='" + description + '\'' +
                ", strArea='" + strArea + '\'' +
                '}';
    }
}
