package sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class WeatherControler {
    private String inputFileName;
    // 1f36b08243530e8fef8d1aeff4520b86
    private String apiKey;

    public WeatherControler(String inputFileName, String apiKey) throws IOException {
        this.inputFileName = inputFileName;
        this.apiKey = apiKey;
        Initialize();
    }

    @FXML
    public void Initialize() throws IOException {
        //TODO: citire fisier, initalizare dropdown-uri orase si tari
        UpdateWeatherData();
    }

    private void UpdateWeatherData() throws IOException {
        String cityName = "Bucharest";
        String countryCode = "RO";
        ApiDataModel apiDataModel = GetWeatherData(cityName, countryCode);
    }

    private ApiDataModel GetWeatherData(String cityName, String countryCode) throws IOException {
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + apiKey;
        ApiClient apiClient = new ApiClient();
        String responseJSON = apiClient.GetData(urlString);
        ApiDataModel apiData = new ApiDataModel();

        JsonObject jsonObject = new JsonParser().parse(responseJSON).getAsJsonObject();
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
