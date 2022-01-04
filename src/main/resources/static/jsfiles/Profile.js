document.addEventListener("DOMContentLoaded", function (event) {


    /**
     * POST Request to our API to ask for a weather at user's location
     * @param lat
     * @param lon
     */
    //uses https://openweathermap.org/ api to retrieve a weather in the current location of the user
    function askWeather(lat, lon) {

        let xmlHttpRequest = new XMLHttpRequest();
        xmlHttpRequest.onreadystatechange = function () {
            if(this.readyState == 4 && this.status == 200) {
                let para = document.createElement("p");
                let h5 = document.createElement("h5");
                let image = document.createElement("img");
                let responseObj = JSON.parse(this.responseText);
                let temp = responseObj.list[0].main.temp
                let weatherDescription = responseObj.list[0].weather[0].description;

                //let imageURL = responseObj.main.weather[0].icon;
                let imageURL = responseObj.list[0].weather[0].icon;
                image.src="https://openweathermap.org/img/wn/" + imageURL + "@2x.png";

                //let bodyElement = document.querySelector("#profileMainSection");
                let bodyElement = document.querySelector("#weather-place");

                let cityName = responseObj.list[0].name;

                para.innerHTML = "The weather is currently " + weatherDescription;
                h5.innerHTML = "The temperature in " + cityName +  " is " + temp + " degrees Celsius."

                bodyElement.appendChild(h5);
                bodyElement.appendChild(para);
                bodyElement.appendChild(image);
                //getCurrentLocation();
            }
        }

        //POST Method
        const url = "http://localhost:8080/weather"
        xmlHttpRequest.open("POST", url , true);
        xmlHttpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlHttpRequest.send('lat=' + lat + "&lon=" + lon);

    };



    //askWeather("Vienna");


    let latitude = null;
    let longitude = null;

    //use browser to define the geolocation of the user and call the function to ask for a weather
    let geo = navigator.geolocation;
    function successfulGeo (position) {
        latitude = position.coords.latitude;
        longitude = position.coords.longitude;

        let lat1 = Math.round(latitude * 10000) / 10000;
        let long1 = Math.round(longitude * 10000) / 10000;

        askWeather(lat1, long1);
    }

    function getCurrentLocation() {
        if (geo != null) {
            geo.getCurrentPosition(successfulGeo);
        }

    }
    getCurrentLocation();


});