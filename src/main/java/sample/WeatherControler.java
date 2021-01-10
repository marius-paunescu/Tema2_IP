package sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.awt.*;
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

    @FXML
    private Label tempLabel = new Label();
    @FXML
    private Label humLabel = new Label();
    @FXML
    private Label windLabel = new Label();
    @FXML
    private ChoiceBox<String> countryDropDown = new ChoiceBox<String>();
    @FXML
    private ChoiceBox<String> cityDropDown = new ChoiceBox<String>();

    public WeatherControler(String inputFileName, String apiKey) throws IOException {
        this.inputFileName = inputFileName;
        this.apiKey = apiKey;
        initialize();
    }

    public WeatherControler() throws IOException {
        this.inputFileName = "input.txt";
        this.apiKey = "1f36b08243530e8fef8d1aeff4520b86";
        initialize();
    }

    @FXML
    private void initialize() throws IOException {
        //TODO: citire fisier, initalizare dropdown-uri orase si tari
        ArrayList<String> countries = new ArrayList<String>();
        ArrayList<String> cities = new ArrayList<String>();

        countries.add("RO");
        countries.add("RU");
        countries.add("FR");
        cities.add("Bucharest");
        cities.add("Moscow");
        cities.add("Tarascon");

        countryDropDown.setItems(FXCollections.observableList(countries));
        cityDropDown.setItems(FXCollections.observableList(cities));

        countryDropDown.getSelectionModel().selectFirst();
        cityDropDown.getSelectionModel().selectFirst();

        UpdateWeatherData();
    }

    private void UpdateWeatherData() throws IOException {
        String cityName = "Bucharest";
        cityName = cityDropDown.getSelectionModel().getSelectedItem();
        String countryCode = "RO";
        countryCode = countryDropDown.getSelectionModel().getSelectedItem();
        ApiDataModel apiDataModel = GetWeatherData(cityName, countryCode);

        tempLabel.setText("Temperature: " + String.valueOf(apiDataModel.mainWeather.temp));
        humLabel.setText("Humidity: " + String.valueOf(apiDataModel.mainWeather.humidity));
        windLabel.setText("Wind: " + String.valueOf(apiDataModel.windSpeed));


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

    @FXML
    private void handleMouseClick() throws IOException {
        UpdateWeatherData();
    }

}
