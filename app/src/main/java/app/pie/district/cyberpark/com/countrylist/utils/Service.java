package app.pie.district.cyberpark.com.countrylist.utils;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by libin on 22/12/2017.
 */

public interface Service {

    @GET("/country/get/all")
    Call<JsonObject> readTeamArray();

}
