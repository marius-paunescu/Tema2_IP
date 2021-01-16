package testing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import sample.CountryModel;

public class CountryModelTest {
    @Test
    public void testAddCites(){
        CountryModel countryModel = new CountryModel("RO");
        countryModel.AddCity("Bucharest");
        countryModel.AddCity("Cluj");
        countryModel.AddCity("Iasi");
        assertEquals("Bucharest",countryModel.cities.get(0));
        assertEquals("Cluj",countryModel.cities.get(1));
        assertEquals("Iasi",countryModel.cities.get(2));
    }

    @Test
    public void testAddCitiesDuplicate(){
        CountryModel countryModel = new CountryModel("RO");
        countryModel.AddCity("Timisoara");
        countryModel.AddCity("Iasi");
        countryModel.AddCity("Iasi");
        countryModel.AddCity("Iasi");
        assertEquals("Timisoara",countryModel.cities.get(0));
        assertEquals("Iasi",countryModel.cities.get(1));
        assertEquals(2, countryModel.cities.size());
    }

}
