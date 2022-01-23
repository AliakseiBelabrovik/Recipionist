package com.example.recipionist.recipionistapi.Models.Cocktail;

import lombok.Builder;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Builder
public class CocktailGlass {
    protected String glass;

    public CocktailGlass(String name) {
        this.glass = name;
    }

    public String getGlass() {
        return glass;
    }

    @Override
    public String toString() {
        return "CocktailGlass{" +
                "glass='" + glass + '\'' +
                '}';
    }


}
