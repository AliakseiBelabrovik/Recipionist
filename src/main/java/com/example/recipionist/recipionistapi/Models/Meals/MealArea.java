package com.example.recipionist.recipionistapi.Models.Meals;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MealArea {

    protected String area;

    public MealArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }




}
