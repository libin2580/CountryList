package app.pie.district.cyberpark.com.countrylist.database;

import java.util.ArrayList;

import app.pie.district.cyberpark.com.countrylist.model.Countrydb_model;

/**
 * Created by libin on 22/12/2017.
 */
public interface CountryDb_Listener {

    public void addCity(Countrydb_model city);

    public ArrayList<Countrydb_model> getAllCity();

    public int getCityCount();
}
