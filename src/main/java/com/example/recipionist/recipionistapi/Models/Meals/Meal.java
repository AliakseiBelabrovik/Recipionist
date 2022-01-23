package com.example.recipionist.recipionistapi.Models.Meals;

import com.example.recipionist.recipionistapi.Models.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
import java.util.List;

@XmlRootElement
@Entity
@Table(
        name = "meals",
        uniqueConstraints = @UniqueConstraint(
                name = "meal_name_unique",
                columnNames = "name"
        )
        )
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Meal {

    @Id
    @SequenceGenerator(
            name = "meals_id_seq",
            sequenceName = "meals_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "meals_id_seq"
    )
    @Column(name = "id", updatable = false)
    protected Long id;

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    protected String mealName;

    //TODO Outsource in MealArea Klasse with annotation Embedded and Embeddable
    @Column(name = "area", columnDefinition = "TEXT")
    protected String area;

    @Column(name = "thumbnail", columnDefinition = "TEXT")
    protected String thumbnail;

    @Column(name = "instructions", columnDefinition = "TEXT")
    protected String instructions;

    @Column(name = "youtube_link", columnDefinition = "TEXT")
    protected String youtubeLink;

    @Column(name = "tags", columnDefinition = "TEXT")
    protected String tags;



    @ManyToOne(
            //optional = false
    )
    @JoinColumn(
            name = "meal_category_id", //column in meals
            referencedColumnName = "id" //is foreign key to id attribute in class MealCategory
    )
    protected MealCategory mealCategory;


    public MealCategory getMealCategory() {
        return mealCategory;
    }

    public void setMealCategory(MealCategory mealCategory) {
        this.mealCategory = mealCategory;
    }
    /*
    public void setMealCategory(MealCategory mealCategory) {
        setMealCategory(mealCategory, true);
    }
    public void setMealCategory(MealCategory mealCategory, boolean add) {
        this.mealCategory = mealCategory;
        if (mealCategory != null && add) {
            mealCategory.addMeal(this, false);
        }
    }
     */


    @ManyToOne(
            //optional = false
    )
    @JoinColumn(
            name = "user_id", //column in meals
            referencedColumnName = "id" //is foreign key to id attribute in class User
    )
    private User user;

    /**
     * Ändern!! Als Relationships darstellen!!!!
     */
    @Transient //will not be a row in our database
    protected ArrayList<String> ingredients;
    @Transient //will not be a row in our database
    protected ArrayList<String> measures;

    /**
     * Diese Variable wurde als Relationship oben bereits behandelt.
     * Wir müssen Methoden umschreiben, damit disese Variable nicht benutzt wird
     */
    @Transient //will not be in our database
    protected String category;

    /**
     * Diese Variablen haben wir nicht gebraucht
     */
    @Transient //will not be a row in our database
    protected String imageSrc;
    @Transient //will not be a row in our database
    protected String drinkAlternate;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }

    public String getMealName() {
        return mealName;
    }

    public String getDrinkAlternate() {
        return drinkAlternate;
    }

    public String getCategory() {
        return category;
    }

    public String getArea() {
        return area;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getTags() {
        return tags;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public ArrayList<String> getMeasures() {
        return measures;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public Meal(Long id, String mealName, String drinkAlternate, String category, String area, String thumbnail, String instructions,
                String tags, ArrayList<String> ingredients, ArrayList<String> measures, String imageSrc){
        this.id = id;
        this.mealName = mealName;
        this.drinkAlternate = drinkAlternate;
        this.category = category;
        this.area = area;
        this.thumbnail = thumbnail;
        this.instructions = instructions;
        this.tags = tags;
        this.ingredients = ingredients;
        this.measures = measures;
        this.imageSrc = imageSrc;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", mealName='" + mealName + '\'' +
                ", area='" + area + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", instructions='" + instructions + '\'' +
                ", youtubeLink='" + youtubeLink + '\'' +
                ", tags='" + tags + '\'' +
                ", mealCategory=" + mealCategory +
                ", user=" + user +
                ", category='" + category + '\'' +
                ", imageSrc='" + imageSrc + '\'' +
                ", drinkAlternate='" + drinkAlternate + '\'' +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public void setDrinkAlternate(String drinkAlternate) {
        this.drinkAlternate = drinkAlternate;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setMeasures(ArrayList<String> measures) {
        this.measures = measures;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }
}
