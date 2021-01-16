package sample;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;


import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class WeatherControler {
    private String inputFileName;
    // 1f36b08243530e8fef8d1aeff4520b86
    private String apiKey;
    private LocationModel locationModel = new LocationModel();

    @FXML
    private Label tempLabel = new Label();
    @FXML
    private Label humLabel = new Label();
    @FXML
    private Label windLabel = new Label();
    @FXML
    private Label cityLabel = new Label();
    @FXML
    private Label timeLabel = new Label();
    @FXML
    private Label descriptionLabel = new Label();
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
        this.inputFileName = "/Users/mariuspaunescu/Desktop/tema2_ip/Tema2_IP/src/main/java/sample/input.txt";
        this.apiKey = "1f36b08243530e8fef8d1aeff4520b86";
        initialize();
    }

    @FXML
    private void initialize() throws IOException {

        String[][] fileData = new String[21][];
        int index = 0;
        File file = new File(inputFileName);
        Scanner input = new Scanner(file);

        while (input.hasNextLine() && index < fileData.length) {    //file reading
            fileData[index] = input.nextLine().split(" ");
            if(index != 0){     //first index in null
                locationModel.AddCity(fileData[index][4] ,fileData[index][1]);
            }
            index++;
        }

        countryDropDown.setItems(FXCollections.observableList(locationModel.GetCountries()));
        cityDropDown.setItems(FXCollections.observableList(locationModel.GetCities(locationModel.GetCountries().get(0))));

        countryDropDown.getSelectionModel().selectFirst();
        cityDropDown.getSelectionModel().selectFirst();
        countryDropDown.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
                int index = newNumber.intValue();
                String country = locationModel.countries.get(index).country;
                cityDropDown.setItems(FXCollections.observableList(locationModel.GetCities(country)));
                cityDropDown.getSelectionModel().selectFirst();
            }
        });

        UpdateWeatherData();
    }

    private void UpdateWeatherData() throws IOException {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, MMM dd; HH:mm");
        String formattedDate = myDateObj.format(myFormatObj);
        String countryCode = "RO";
        String cityName = "Bucharest";
        countryCode = countryDropDown.getSelectionModel().getSelectedItem();
        cityName = cityDropDown.getSelectionModel().getSelectedItem();
        ApiDataModel apiDataModel = GetWeatherData(cityName, countryCode);

        tempLabel.setText("Temperature: " + String.valueOf(new DecimalFormat("##.#").format(apiDataModel.mainWeather.temp - apiDataModel.mainWeather.kelvin)) + "C");
        humLabel.setText("Humidity: " + String.valueOf(apiDataModel.mainWeather.humidity) + "%");
        windLabel.setText("Wind: " + String.valueOf(apiDataModel.windSpeed) + "km/h");
        cityLabel.setText(String.valueOf(cityName));
        timeLabel.setText(String.valueOf(formattedDate));
        descriptionLabel.setText(String.valueOf(apiDataModel.weather.description));

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

    @FXML
    private void handleSelected() throws IOException {

    }

}
