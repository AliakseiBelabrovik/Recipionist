package com.example.recipionist.recipionistapi.Models.Meals;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealCategory {

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

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    protected String categoryName;

    @Column(name = "thumbnail", nullable = false, columnDefinition = "TEXT")
    protected String thumbnail;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    protected String description;

    @Column(name = "area", columnDefinition = "TEXT")
    protected String strArea;

    @OneToMany(
            mappedBy = "mealCategory")
    private List<Meal> meals;

    public void addMeal(Meal meal) {
        if (meals == null) {
            meals = new ArrayList<>();
        }
        meals.add(meal);
    }


    public MealCategory(String category, String thumbnail, String description, String strArea) {
        this.categoryName = category;
        this.thumbnail = thumbnail;
        this.description = description;
        this.strArea = strArea;
    }

    public MealCategory(Long id, String category, String thumbnail, String description, String strArea) {
        this.id = id;
        this.categoryName = category;
        this.thumbnail = thumbnail;
        this.description = description;
        this.strArea = strArea;
    }


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
