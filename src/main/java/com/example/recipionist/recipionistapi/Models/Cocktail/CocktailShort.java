package com.example.recipionist.recipionistapi.Models.Cocktail;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CocktailShort {

    protected String id;
    protected String mealName;
    protected String thumbnail;

    public CocktailShort(String id, String mealName, String thumbnail) {
        this.id = id;
        this.mealName = mealName;
        this.thumbnail = thumbnail;
    }
    public String getId() {
        return id;
    }

    public String getMealName() {
        return mealName;
    }

    public String getThumbnail() {
        return thumbnail;
    }


}
