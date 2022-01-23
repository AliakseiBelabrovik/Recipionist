package com.example.recipionist.recipionistapi.Models.Cocktail;

import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement

@Entity
@Table(
        name = "cocktail_category",
        uniqueConstraints = @UniqueConstraint(
                name = "cocktail_category_name_unique",
                columnNames = "name"
        )
)
@AllArgsConstructor
@Builder
public class CocktailCategory {
    @Id
    @SequenceGenerator(
            name = "CocktailCategory_ID_seq",
            sequenceName = "CocktailCategory_ID_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "CocktailCategory_ID_seq"
    )
    @Column(name = "ID", updatable = false)
    private Long id;

    @Column(name = "Name", nullable = false, columnDefinition = "TEXT", unique = true)
    protected String category;

    @JsonIgnore
    private List<Cocktail> cocktails;


    public CocktailCategory() {

    }
    public void addCocktail(Cocktail cocktail) {
        addCocktail(cocktail, true);
    }

    public void addCocktail(Cocktail cocktail, boolean set) {
        if (cocktails == null) {
            cocktails = new ArrayList<>();
        }
        if (cocktails != null) {
            if (this.getCocktails().contains(cocktail)) {
                this.getCocktails().set(this.getCocktails().indexOf(cocktail), cocktail);
            } else {
                this.getCocktails().add(cocktail);
            }
            if (set) {
                cocktail.setCocktailCategory(this, false);
            }
        }
    }

    public void removeCocktail(Cocktail cocktail) {
        this.getCocktails().remove(cocktail);
        cocktail.setCocktailCategory(null);
    }

    @JsonManagedReference
    public List<Cocktail> getCocktails() {
        return cocktails;
    }

    public void setCocktails(List<Cocktail> cocktails) {
        this.cocktails = cocktails;
    }

    @Override
    public String toString() {
        return "CocktailCategory{" +
                "id='" + id + '\'' +
                ", category='" + category + '\''+
                '}';
    }


    public CocktailCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
