package com.example.recipionist.recipionistapi.Services;

import com.example.recipionist.recipionistapi.DataLoader;
import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import com.example.recipionist.recipionistapi.Models.Meals.MealArea;
import com.example.recipionist.recipionistapi.Models.Meals.MealCategory;
import com.example.recipionist.recipionistapi.Repositories.IngredientRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MealAreaService {

    DataLoader dataLoader = new DataLoader();
    private MealService mealService;

    @Autowired
    public MealAreaService(MealService mealService) {
        this.mealService = mealService;
    }

    public ArrayList<MealArea> getMealAreas(String data){

        JSONObject mealObj = dataLoader.loadData(data);
        JSONArray jsonArray = null;
        if (mealObj.containsKey("meals")) {
            jsonArray= (JSONArray) mealObj.remove("meals");
        }
        //System.out.println(jsonArray);

        ArrayList<MealArea> areaArrayList = new ArrayList<>();

        while (!jsonArray.isEmpty()){

            JSONObject category = (JSONObject) jsonArray.get(jsonArray.size()-1);
            jsonArray.remove(jsonArray.size()-1);

            MealArea mealArea = new MealArea(
                    (String) category.get("strArea")
            );
            areaArrayList.add(mealArea);

        }

        return areaArrayList;
    }


    public ArrayList<MealArea> getLocalAreas(ArrayList<MealArea> allAreas) {

        //get all area names that we have in the local database
        ArrayList<String> areaNames =mealService.getAllAreasOfAllLocalMeals(mealService.getAllMeals());
        ArrayList<MealArea> onlyLocalAreas = new ArrayList<>();
        for (String areaName:
             areaNames) {
            MealArea temporaryMealArea = MealArea.builder()
                    .area(areaName)
                    .build();
            System.out.println("areaName = " + areaName);
            boolean exists = false;
            for (MealArea mealArea:
                 allAreas) {
                if (areaName.equals(mealArea.getArea())) {
                    exists = true;
                }
            }
            //if the local meal area is not present in the reply of the MealDB, add it to the list of meal areas
            if (!exists) {
                onlyLocalAreas.add(temporaryMealArea);
            }
        }
        return onlyLocalAreas;
    }
}
