package com.example.recipionist.recipionistapi.Services;

import com.example.recipionist.recipionistapi.DataLoader;
import com.example.recipionist.recipionistapi.Models.Meals.MealArea;
import com.example.recipionist.recipionistapi.Models.Meals.MealCategory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MealAreaService {
    DataLoader dataLoader = new DataLoader();

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








}
