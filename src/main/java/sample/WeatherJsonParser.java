package sample;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherJsonParser {
    public ApiDataModel parseApiDataModel(String jsonData){
        ApiDataModel apiData = new ApiDataModel();

        JsonObject jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
        apiData.name = jsonObject.get("name").getAsString();
        apiData.id = jsonObject.get("id").getAsInt();
        apiData.cod = jsonObject.get("cod").getAsInt();

        apiData.coords = new CoordsModel();
        apiData.coords.lat = jsonObject.get("coord").getAsJsonObject().get("lat").getAsDouble();
        apiData.coords.lon = jsonObject.get("coord").getAsJsonObject().get("lon").getAsDouble();

        WeatherModel weatherModel = new WeatherModel();
        weatherModel.id = jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("id").getAsInt();
        weatherModel.description = jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString();
        weatherModel.main = jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString();
        weatherModel.icon = jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
        apiData.weather = weatherModel;

        apiData.mainWeather = new MainWeatherModel();
        apiData.mainWeather.temp = jsonObject.get("main").getAsJsonObject().get("temp").getAsDouble();
        apiData.mainWeather.temp_min = jsonObject.get("main").getAsJsonObject().get("temp_min").getAsDouble();
        apiData.mainWeather.temp_max = jsonObject.get("main").getAsJsonObject().get("temp_max").getAsDouble();
        apiData.mainWeather.pressure = jsonObject.get("main").getAsJsonObject().get("pressure").getAsInt();
        apiData.mainWeather.humidity = jsonObject.get("main").getAsJsonObject().get("humidity").getAsInt();

        apiData.windSpeed = jsonObject.get("wind").getAsJsonObject().get("speed").getAsDouble();
        apiData.countryCode = jsonObject.get("sys").getAsJsonObject().get("country").getAsString();

        return apiData;
    }



}
