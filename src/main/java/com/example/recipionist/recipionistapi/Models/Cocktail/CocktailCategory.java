package com.example.recipionist.recipionistapi.Models.Cocktail;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity(name = "cocktail_category")
@Table(name = "cocktail_category")
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

    public CocktailCategory() {

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
