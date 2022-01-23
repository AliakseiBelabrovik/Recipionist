package com.example.recipionist.recipionistapi.Models.Meals;

import lombok.Builder;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Builder
public class MealArea {

    protected String area;

    public MealArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    @Override
    public String toString() {
        return "MealArea{" +
                "area='" + area + '\'' +
                '}';
    }
}
