package app.pie.district.cyberpark.com.countrylist;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import app.pie.district.cyberpark.com.countrylist.adapter.DataAdapter;
import app.pie.district.cyberpark.com.countrylist.database.CountryDB_Handler;
import app.pie.district.cyberpark.com.countrylist.model.Countrydb_model;
import app.pie.district.cyberpark.com.countrylist.utils.Client;
import app.pie.district.cyberpark.com.countrylist.utils.CountrysDb_NetworkUtils;
import app.pie.district.cyberpark.com.countrylist.utils.InternetConnection;
import app.pie.district.cyberpark.com.countrylist.utils.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by libin on 22/12/2017.
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataAdapter adapter;
    KProgressHUD hud;
    EditText edit_search;
    TextView pink_knight_hading_txt;
    ImageView search_btn,close_buton_edit_text,filter_buton,filter_buton1;
    CountryDB_Handler handler;
    ArrayList<Countrydb_model> country_list = new ArrayList<Countrydb_model>();
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pink_knight_hading_txt=(TextView)findViewById(R.id.country_head);
        pink_knight_hading_txt.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Mark Simonson - Proxima Nova Alt Regular-webfont.ttf"));
        close_buton_edit_text=(ImageView) findViewById(R.id.close_buton);
        edit_search=(EditText)findViewById(R.id.edit_search);

        edit_search.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Mark Simonson - Proxima Nova Alt Regular-webfont.ttf"));
        search_btn=   (ImageView) findViewById(R.id.search);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pink_knight_hading_txt.setVisibility(View.GONE);
                search_btn.setVisibility(View.GONE);
                close_buton_edit_text.setVisibility(View.VISIBLE);
                edit_search.setVisibility(View.VISIBLE);
                filter_buton.setVisibility(View.GONE);
                filter_buton1.setVisibility(View.GONE);
                edit_search.requestFocus();
                edit_search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                        if (adapter != null) adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

            }
        });
        close_buton_edit_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter_buton1.setVisibility(View.VISIBLE);
                filter_buton.setVisibility(View.VISIBLE);
                pink_knight_hading_txt.setVisibility(View.VISIBLE);
                search_btn.setVisibility(View.VISIBLE);
                close_buton_edit_text.setVisibility(View.GONE);
                edit_search.setVisibility(View.GONE);
                handler = new CountryDB_Handler(getApplicationContext());
                CountrysDb_NetworkUtils utils = new CountrysDb_NetworkUtils(getApplicationContext());
                if (handler.getCityCount() == 0 && utils.isConnectingToInternet()) {
                    initViews();
                    func();
                } else {
                    Alerter.create(MainActivity.this)
                            .setTitle("Please Turn on your Internet")
                            //  .setText("selct doctor")
                            .setBackgroundColor(R.color.yellow)
                            .setDuration(1000)
                            .setIcon(R.drawable.alerter_ic_notifications)
                            .show();
                    initViews();
                    country_list = handler.getAllCity();
                    HashSet<Countrydb_model> hashSet = new HashSet<Countrydb_model>();
                    hashSet.addAll(country_list);
                    country_list.clear();
                    country_list.addAll(hashSet);
                    adapter = new DataAdapter(country_list,MainActivity.this);
                    recyclerView.setAdapter(adapter);
                }

            }
        });
        handler = new CountryDB_Handler(getApplicationContext());
        CountrysDb_NetworkUtils utils = new CountrysDb_NetworkUtils(getApplicationContext());
        if (handler.getCityCount() == 0 && utils.isConnectingToInternet()) {
            initViews();
            func();
        } else {
            Alerter.create(MainActivity.this)
                    .setTitle("Please Turn on your Internet")
                    //  .setText("selct doctor")
                    .setBackgroundColor(R.color.yellow)
                    .setDuration(1000)
                    .setIcon(R.drawable.alerter_ic_notifications)
                    .show();
            initViews();
            country_list = handler.getAllCity();
            HashSet<Countrydb_model> hashSet = new HashSet<Countrydb_model>();
            hashSet.addAll(country_list);
            country_list.clear();
            country_list.addAll(hashSet);
            adapter = new DataAdapter(country_list,MainActivity.this);
            recyclerView.setAdapter(adapter);
        }

        filter_buton=   (ImageView) findViewById(R.id.filter_buton);
        filter_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // epartmentlist.add(dep_modl);
                Collections.sort(country_list, new Comparator<Countrydb_model>() {
                    @Override
                    public int compare(Countrydb_model lhs, Countrydb_model rhs) {
                        return lhs.getName().compareTo(rhs.getName());
                    }
                });
                adapter = new DataAdapter(country_list,MainActivity.this);
                recyclerView.setAdapter(adapter);


            }
        });
        filter_buton1=   (ImageView) findViewById(R.id.filter_buton_dess);
        filter_buton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // epartmentlist.add(dep_modl);
                Collections.sort(country_list, new Comparator<Countrydb_model>() {
                    @Override
                    public int compare(Countrydb_model lhs, Countrydb_model rhs) {
                        return rhs.getName().compareTo(lhs.getName());
                    }
                });

                adapter = new DataAdapter(country_list,MainActivity.this);
                recyclerView.setAdapter(adapter);


            }
        });
    }
    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.listView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void func() {
     country_list.clear();
        handler.removeAll();
        if (InternetConnection.checkConnection(getApplicationContext())) {
            hud= KProgressHUD.create(MainActivity.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setCancellable(true)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f)
                    .show();

            Service serviceAPI = Client.getClient();
            Call<JsonObject> loadTeamCall = serviceAPI.readTeamArray();
            loadTeamCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    System.out.println("<<<<<<<<<<<<<<<<<<<<result>>>>>>>>>>>>>>" + response.toString());
                    String teamString = response.body().toString();
                    System.out.println("_________status_____________" + teamString);



                    if(response.isSuccessful()) {
                        hud.dismiss();
                        try {
                            JSONObject jsonObj = new JSONObject(teamString);
                            String status = jsonObj.getString("RestResponse");
                            System.out.println("_________status_____________" + status);
                       // JSONObject    menu = jsonObj.getJSONObject("result");
                           JSONObject menu = jsonObj.getJSONObject("RestResponse");

                            JSONArray jsonArray=menu.getJSONArray("result");

                            System.out.println("___________data___________" + jsonArray);


                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject j = jsonArray.getJSONObject(i);
                                String  name = j.getString("name");
                                String alpha2_code= j.getString("alpha2_code");
                                String alpha3_code= j.getString("alpha3_code");
                                System.out.println("___________name__________" + name);
                                Countrydb_model city = new Countrydb_model();


                                if (handler.checkExistance(name)>0) {
                                   // Toast.makeText(getApplicationContext(),"Al  ready Added",Toast.LENGTH_SHORT).show();
                                }
                                else {

                                    city.setName(name);
                                    handler.addCity(city);
                                   // handler.insertData1(holder.tvName.getText().toString());

                                }



                            }

                        }catch (JSONException ew) {
                            ew.printStackTrace();
                        }



                        country_list = handler.getAllCity();
                        HashSet<Countrydb_model> hashSet = new HashSet<Countrydb_model>();
                        hashSet.addAll(country_list);
                        country_list.clear();
                        country_list.addAll(hashSet);
                        adapter = new DataAdapter(country_list,MainActivity.this);
                        recyclerView.setAdapter(adapter);

                    } else {
                        //Snackbar.make(parentView, R.string.string_some_thing_wrong, Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject>  call, Throwable t) {
                    hud.dismiss();
                }
            });

        } else {
            //Snackbar.make(parentView, "No Internet", Snackbar.LENGTH_LONG).show();
        }
    }


}