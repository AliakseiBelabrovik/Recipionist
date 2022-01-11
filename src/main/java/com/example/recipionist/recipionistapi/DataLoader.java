package com.example.recipionist.recipionistapi;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
public class DataLoader {
    JSONObject obj;
    JSONParser parser = new JSONParser();

    public DataLoader(){}

    public JSONObject loadData(String data){
        try {
            obj = (JSONObject) parser.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return obj;

    }
}
