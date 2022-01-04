package com.example.recipionist.recipionistapi.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class TemplateController {
    @Autowired
    private RestTemplate restTemplate;
    private final String APIKEY = "bd1f07cb10d46551c60fa35f3094cf44";
    private final String UNIT = "metric";
    private final String URL = "https://api.openweathermap.org/data/2.5/weather?q=";


    @RequestMapping("main")
    public String getToMainPage() {
       return "Main";
    }
    @RequestMapping("recipes")
    public String goToRecipesPage() {
        return "Recipes";
    }
    @RequestMapping("cocktails")
    public String goToCocktailsPage() {
        return "Cocktails";
    }
    @RequestMapping("session/profile")
    public String goToProfilePage() {
        return "session/Profile";
    }
    @RequestMapping("session/login")
    public String goToLoginPage() {
        return "session/Login";
    }
    @RequestMapping("session/createrecipe")
    public String goToCreateRecipe() {
        return "session/CreateRecipe";
    }

    @RequestMapping()
    public String gotMMM() {
        return "redirect:/main";
    }

    /*
    @PostMapping("/weather")
    @ResponseBody
    public String getWeather(@RequestParam("cityName") String cityName) {
        String weatherUrl = URL + cityName + "&units=" + UNIT + "&appid=" + APIKEY;
        System.out.println("I am in getWeather for city " + cityName);
        String data = restTemplate.getForObject(weatherUrl, String.class);
        System.out.println("I get response " + data);
        return data;
    }
     */
    @PostMapping("/weather")
    @ResponseBody
    public String getWeather(@RequestParam("lat") String lat, @RequestParam("lon") String lon) {
        String weatherUrl = "https://api.openweathermap.org/data/2.5/find?lat=" + lat + "&lon=" + lon + "&cnt=1&appid=" + APIKEY + "&units=metric";
        String data = restTemplate.getForObject(weatherUrl, String.class);
        return data;
    }


}
