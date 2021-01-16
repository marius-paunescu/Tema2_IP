package sample;

import java.util.ArrayList;

public class CountryModel {
    public String country;
    public ArrayList<String> cities;

    public CountryModel(String country) {
        this.country = country;
        cities = new ArrayList<String>();
    }

    public void AddCity(String city){
        boolean exists = false;
        for (int i=0; i<cities.size(); i++)
            if (cities.get(i).equals(city)){
                exists = true;
                break;
            }

        if (!exists)
            cities.add(city);
    }
}
