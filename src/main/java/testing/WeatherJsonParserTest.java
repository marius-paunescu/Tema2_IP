package testing;

import org.junit.jupiter.api.Test;
import sample.ApiClient;
import sample.ApiDataModel;
import sample.WeatherJsonParser;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WeatherJsonParserTest {
    @Test
    public void testParseApiDataModel() throws IOException {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Bucharest&appid=1f36b08243530e8fef8d1aeff4520b86";
        WeatherJsonParser weatherJsonParser = new WeatherJsonParser();
        ApiClient apiClientTest = mock(ApiClient.class);
        when(apiClientTest.GetData(url))
                .thenReturn("{\"coord\":{\"lon\":26.1063,\"lat\":44.4323},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"base\":\"stations\",\"main\":{\"temp\":269.84,\"feels_like\":264.58,\"temp_min\":269.15,\"temp_max\":270.93,\"pressure\":1015,\"humidity\":80},\"visibility\":10000,\"wind\":{\"speed\":3.6,\"deg\":60},\"clouds\":{\"all\":0},\"dt\":1610816494,\"sys\":{\"type\":1,\"id\":6911,\"country\":\"RO\",\"sunrise\":1610776066,\"sunset\":1610809349},\"timezone\":7200,\"id\":683506,\"name\":\"Bucharest\",\"cod\":200}");
        ApiDataModel apiDataModel = weatherJsonParser.parseApiDataModel(apiClientTest.GetData(url));
        assertEquals("RO" ,apiDataModel.countryCode);
        assertEquals(200 ,apiDataModel.cod);
        assertEquals(683506 ,apiDataModel.id);
        assertEquals(44.4323 ,apiDataModel.coords.lat);
        assertEquals(26.1063 ,apiDataModel.coords.lon);
    }

}
