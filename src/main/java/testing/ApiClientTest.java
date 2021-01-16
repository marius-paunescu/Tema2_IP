package testing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import sample.ApiClient;

import java.io.IOException;

public class ApiClientTest {
    @Test
    public void testGetData() throws IOException {
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=Bucharest&appid=1f36b08243530e8fef8d1aeff4520b86";
        ApiClient apiClient = new ApiClient();
        String responseJSON = apiClient.GetData(urlString);
        assertNotNull(responseJSON);
    }
}
