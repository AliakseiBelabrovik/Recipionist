package com.example.recipionist.recipionistapi.Models.Cocktail;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CocktailGlass {
    protected String glass;

    public CocktailGlass(String name) {
        this.glass = name;
    }

    public String getGlass() {
        return glass;
    }




}
