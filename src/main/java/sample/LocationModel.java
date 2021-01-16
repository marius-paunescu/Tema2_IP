package sample;

import java.util.ArrayList;

public class LocationModel {
    ArrayList<CountryModel> countries = new ArrayList<CountryModel>();
    public void AddCity(String countryName, String cityName){
        int existingCountryIndex = -1;
        for (int i = 0; i<countries.size(); i++)
            if (countries.get(i).country.equals(countryName)){
                existingCountryIndex = i;
                break;
            }

        if (existingCountryIndex == -1){
            countries.add(new CountryModel(countryName));
            existingCountryIndex = countries.size() - 1;
        }
        countries.get(existingCountryIndex).AddCity(cityName);
    }

    public ArrayList<String> GetCountries(){
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i<countries.size(); i++){
            result.add(countries.get(i).country);
        }
        return result;
    }

    public ArrayList<String> GetCities(String country){
        for (int i = 0; i<countries.size(); i++)
           if (countries.get(i).country.equals(country))
               return countries.get(i).cities;

        return new ArrayList<String>();
    }

}
