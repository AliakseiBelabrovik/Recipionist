package com.example.recipionist.recipionistapi.Models.Cocktail;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class Cocktail {
    protected String id;
    protected String name;
    protected String category;
    protected String alcohol;
    protected String glass;
    protected String instructions;
    protected String thumbnail;
    protected ArrayList<String> ingredients;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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

    protected ArrayList<String> measures;

    public Cocktail(String id, String name, String category, String alcohol, String glass, String instructions, String thumbnail, ArrayList<String> ingredients, ArrayList<String> measures) {
        this.id = id;
        this.name = name;
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
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", alcohol='" + alcohol + '\'' +
                ", glass='" + glass + '\'' +
                ", instructions='" + instructions + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", ingredients=" + ingredients +
                ", measures=" + measures +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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
