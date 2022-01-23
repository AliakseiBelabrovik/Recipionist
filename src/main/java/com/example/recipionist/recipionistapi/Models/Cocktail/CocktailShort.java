package com.example.recipionist.recipionistapi.Models.Cocktail;

import lombok.Builder;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Builder
public class CocktailShort {

    protected Long id;
    protected String cocktailName;
    protected String thumbnail;

    public CocktailShort(Long id, String cocktailName, String thumbnail) {
        this.id = id;
        this.cocktailName = cocktailName;
        this.thumbnail = thumbnail;
    }
    public Long getId() {
        return id;
    }

    public String getCocktailName() {
        return cocktailName;
    }

    public String getThumbnail() {
        return thumbnail;
    }


}
