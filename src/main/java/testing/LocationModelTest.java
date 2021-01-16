package testing;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import sample.LocationModel;

public class LocationModelTest {
    @Test
    public void testAddCity(){
        LocationModel locationModel = new LocationModel();
        locationModel.AddCity("RO", "Bucharest");
        assertEquals(1, locationModel.GetCities(locationModel.GetCountries().get(0)).size());
    }

}
