package com.example.recipionist.recipionistapi.Models.Cocktail;


import com.example.recipionist.recipionistapi.Models.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement
@Entity
@Table(
        name = "cocktails",
        uniqueConstraints = @UniqueConstraint(
                name = "cocktail_name_unique",
                columnNames = "name"
        )
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cocktail {

    @Id
    @SequenceGenerator(
            name = "cocktails_id_seq",
            sequenceName = "cocktails_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cocktails_id_seq"
    )
    @Column(name = "id", updatable = false)
    protected Long id;

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    protected String cocktailName;

    //TODO Outsource in MealArea Klasse with annotation Embedded and Embeddable
    @Column(name = "alcohol", columnDefinition = "TEXT")
    protected String alcohol;

    @Column(name = "thumbnail", columnDefinition = "TEXT")
    protected String thumbnail;

    @Column(name = "instructions", columnDefinition = "TEXT")
    protected String instructions;

    @Column(name = "glass", columnDefinition = "TEXT")
    protected String glass;

    @ManyToOne(
            //optional = false
    )
    @JoinColumn(
            name = "cocktail_category_id", //column in cocktails
            referencedColumnName = "id" //is foreign key to id attribute in class MealCategory
    )
    protected CocktailCategory cocktailCategory;


    @JsonBackReference
    public CocktailCategory getCocktailCategory() {
        return cocktailCategory;
    }

    public void setCocktailCategory(CocktailCategory cocktailCategory) {
        this.cocktailCategory = cocktailCategory;
    }
//    public void setCocktailCategory(CocktailCategory cocktailCategory, boolean add) {
//        this.cocktailCategory = cocktailCategory;
//        if (cocktailCategory != null && add) {
//            cocktailCategory.addCocktail(this, false);
//        }
//    }

    @ManyToOne(
            //optional = false
    )
    @JoinColumn(
            name = "user_id", //column in meals
            referencedColumnName = "id" //is foreign key to id attribute in class User
    )
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

//    public void setUser(User user, boolean add) {
//        this.user = user;
//        if (user != null && add) {
//            user.addCocktail(this, false);
//        }
//    }

    public User getUser() {
        return user;
    }

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



    public Long getId() {
        return id;
    }

    public String getName() {
        return cocktailName;
    }

    public String getCategory() {
        return category;
    }

    public String getAlcohol() {
        return alcohol;
    }

    public String getGlass() {
        return glass;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public ArrayList<String> getMeasures() {
        return measures;
    }



    public Cocktail(Long id, String name, String category, String alcohol, String glass, String instructions, String thumbnail, ArrayList<String> ingredients, ArrayList<String> measures) {
        this.id = id;
        this.cocktailName = name;
        this.category = category;
        this.alcohol = alcohol;
        this.glass = glass;
        this.instructions = instructions;
        this.thumbnail = thumbnail;
        this.ingredients = ingredients;
        this.measures = measures;
    }

    @Override
    public String toString() {
        return "Cocktail{" +
                "id='" + id + '\'' +
                ", name='" + cocktailName + '\'' +
                ", category='" + category + '\'' +
                ", alcohol='" + alcohol + '\'' +
                ", glass='" + glass + '\'' +
                ", instructions='" + instructions + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", ingredients=" + ingredients +
                ", measures=" + measures +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.cocktailName = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setMeasures(ArrayList<String> measures) {
        this.measures = measures;
    }
}
