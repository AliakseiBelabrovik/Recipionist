package com.example.recipionist.recipionistapi.Models.Meals;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShortMeal {

    public String getId() {
        return id;
    }

    public String getMealName() {
        return mealName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    protected String id;
    protected String mealName;
    protected String thumbnail;

    public ShortMeal(String id, String mealName, String thumbnail){
        this.id = id;
        this.mealName = mealName;
        this.thumbnail = thumbnail;
    }

}
