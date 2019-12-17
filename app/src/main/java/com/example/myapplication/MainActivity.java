package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myapplication.MESSAGE";
    public static HashMap<String, String> parameters = new HashMap<>();
    private static TextView key, fromText;
    private static Spinner Category;
    private static Button Search, Clear;
    private static RadioGroup radio_g;
    private static RadioButton zipRadio, hereRadio, radio_b;
    private static CheckBox nearByCheck, newCheck, usedCheck, unspecifiedCheck, localCheck, freeCheck;
    private static EditText Keyword, Miles, ZipCode;
    private static String categoryText, keywordText, distanceText, zipCodeUser, distanceRadio, spinnerText;
    private static boolean news, used, unspecified, local, free, nearBy;
    private static String[] CategoryArray = {"All", "Art", "Baby", "Books",
            "Clothing, Shoes & Accessories", "Computer/Tablets & Networking", "Health & Beauty", "Music", "Video Game and Console"};
    public String zipFinal;
    private RequestQueue queue;
    public static String zipGlobal = "Nothing";

    private TextInputLayout til, til2;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }


    public void onSearchClick(View view) {

        Keyword = (EditText) findViewById(R.id.KeywordText);
        Category = (Spinner) findViewById(R.id.category);

        //spinner1 =(Spinner) findViewById(R.id.category);

        newCheck = (CheckBox) findViewById(R.id.NewCheck);
        usedCheck = (CheckBox) findViewById(R.id.UsedCheck);
        unspecifiedCheck = (CheckBox) findViewById(R.id.UnspecifiedCheck);

        localCheck = (CheckBox) findViewById(R.id.LocalCheck);
        freeCheck = (CheckBox) findViewById(R.id.FreeCheck);

        nearByCheck = (CheckBox) findViewById(R.id.NearbyCheck);

        fromText = (TextView) findViewById(R.id.FromTitle);
        Miles = (EditText) findViewById(R.id.MilesText);

        ZipCode = (EditText) findViewById(R.id.ZipCodeText);

        radio_g = (RadioGroup) findViewById(R.id.radioGroup);

        zipRadio = (RadioButton) findViewById(R.id.zipRadio);
        hereRadio = (RadioButton) findViewById(R.id.hereRadio);

        Search = (Button) findViewById(R.id.Search);
        Clear = (Button) findViewById(R.id.Clear);



        keywordText = Keyword.getText().toString();
        categoryText = Category.getSelectedItem().toString();
        distanceText = Miles.getText().toString();
        zipCodeUser = ZipCode.getText().toString();

        news = newCheck.isChecked();

        used = usedCheck.isChecked();
        unspecified = unspecifiedCheck.isChecked();

        local = localCheck.isChecked();
        free = freeCheck.isChecked();
        nearBy = nearByCheck.isChecked();

        radio_b = (RadioButton) findViewById(radio_g.getCheckedRadioButtonId());
        distanceRadio = radio_b.getText().toString();

        til = (TextInputLayout) findViewById(R.id.text_input_layout);
        til2 = (TextInputLayout) findViewById(R.id.text_input_layout2);
        if(keywordText.equals("") || (zipRadio.isChecked() && zipCodeUser.equals(""))) {

            if(keywordText.equals("")) {
                til.setError("Please enter mandatory field");
            }

            if(zipRadio.isChecked() && zipCodeUser.equals("")){
                til2.setError("Please enter mandatory field");
            }
            Toast toast = Toast.makeText(getApplicationContext(), "Please fix all fields with errors", Toast.LENGTH_LONG); // initiate the Toast with context, message and duration for the Toast
            toast.show(); // display the Toast

        }

        else {


            //Toast toast = Toast.makeText(getApplicationContext(), keywordText, Toast.LENGTH_LONG); // initiate the Toast with context, message and duration for the Toast
            //toast.show(); // display the Toast
            //Log.v(MainActivity.class.getSimpleName(), Keyword.getText().toString());

            parameters.put("Keyword", keywordText);
            parameters.put("Category", categoryText);

            if (news == true) {
                parameters.put("New", "true");
            } else {
                parameters.put("New", "false");
            }

            if (used == true) {
                parameters.put("Used", "true");
            } else {
                parameters.put("Used", "false");
            }

            if (unspecified == true) {
                parameters.put("Unspecified", "true");
            } else {
                parameters.put("Unspecified", "false");
            }

            if (local == true) {
                parameters.put("Local", "true");
            } else {
                parameters.put("Local", "false");
            }

            if (free == true) {
                parameters.put("Free", "true");
            } else {
                parameters.put("Free", "false");
            }

            if (nearByCheck.isChecked()) {
                parameters.put("NearBySearch", "true");
            } else {
                parameters.put("NearBySearch", "false");
            }

            if (distanceText.equals("")) {
                parameters.put("Miles", "100000");
            } else {
                parameters.put("Miles", distanceText);
            }


            if (distanceRadio.equals("Current Location")) {
                // first StringRequest: getting items searched
                // queue = Volley.newRequestQueue(this);
                //StringRequest stringRequest = searchNameStringRequest();
                //queue.add(stringRequest);


                String url1 = "http://ip-api.com/json";

                queue = Volley.newRequestQueue(this);
                JsonObjectRequest getRequest1 = new JsonObjectRequest(Request.Method.GET, url1, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
// display response
                                try {
                                    JSONObject result = new JSONObject(String.valueOf(response));

                                    setZip(result);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error.Response", "error");
                            }
                        }
                );
                queue.add(getRequest1);
                //parameters.put("zip", zipFinal);
            } else {
                parameters.put("zip", zipCodeUser);
                Intent intent = new Intent(this, FirstCall.class);
                intent.putExtra(EXTRA_MESSAGE, parameters);
                startActivity(intent);

            }
        }


                //Log.d("parameters:", parameters.toString());
        //Log.d("ZIPCODE", zipGlobal);
       // Toast toast1 = Toast.makeText(getApplicationContext(), zipGlobal, Toast.LENGTH_LONG); // initiate the Toast with context, message and duration for the Toast
       // toast1.show();
    }

    public void setZip(JSONObject result){
        try {
            zipFinal = result.getString("zip");
            Log.d("Zip:", zipFinal);
            zipGlobal = zipFinal;
            Log.d("ZipGlobal:", zipGlobal);
            parameters.put("zip", zipGlobal);
            Log.d("ZipGlobal parameters:", parameters.toString());
            Intent intent = new Intent(this, FirstCall.class);
            intent.putExtra(EXTRA_MESSAGE, parameters);
            startActivity(intent);


        }
        catch(JSONException e){
            e.printStackTrace();
        }



    }

    public void onCheckedChanged(View view) {
        nearByCheck = (CheckBox) findViewById(R.id.NearbyCheck);
        fromText = (TextView) findViewById(R.id.FromTitle);
        Miles = (EditText) findViewById(R.id.MilesText);
        radio_g = (RadioGroup) findViewById(R.id.radioGroup);
        ZipCode = (EditText) findViewById(R.id.ZipCodeText);

        if (nearByCheck.isChecked()) {
            fromText.setVisibility(View.VISIBLE);
            Miles.setVisibility(View.VISIBLE);
            radio_g.setVisibility(View.VISIBLE);
            ZipCode.setVisibility(View.VISIBLE);
        } else {
            fromText.setVisibility(View.GONE);
            Miles.setVisibility(View.GONE);
            radio_g.setVisibility(View.GONE);
            ZipCode.setVisibility(View.GONE);
        }


    }

    public void onClearClick(View view) {
        Category = (Spinner) findViewById(R.id.category);
        Keyword = (EditText) findViewById(R.id.KeywordText);
        Miles = (EditText) findViewById(R.id.MilesText);
        ZipCode = (EditText) findViewById(R.id.ZipCodeText);

        fromText = (TextView) findViewById(R.id.FromTitle);
        Miles = (EditText) findViewById(R.id.MilesText);
        radio_g = (RadioGroup) findViewById(R.id.radioGroup);
        nearByCheck = (CheckBox) findViewById(R.id.NearbyCheck);

        til = (TextInputLayout) findViewById(R.id.text_input_layout);
        til2 = (TextInputLayout) findViewById(R.id.text_input_layout2);

        Keyword.setText("");
        Miles.setText("");
        ZipCode.setText("");

        Category.setSelection(0);

        //zipRadio.setChecked(false);
        //hereRadio.setChecked(true);

        fromText.setVisibility(View.GONE);
        Miles.setVisibility(View.GONE);
        radio_g.setVisibility(View.GONE);
        ZipCode.setVisibility(View.GONE);

        //newCheck.setChecked(false);
        //usedCheck.setChecked(false);
        //unspecifiedCheck.setChecked(false);

        //localCheck.setChecked(false);
        //freeCheck.setChecked(false);

        nearByCheck.setChecked(false);


         til.setError("");
         til2.setError("");

    }

    public void zipRadioClicked(View view){

        ZipCode = (EditText) findViewById(R.id.ZipCodeText);
        ZipCode.setEnabled(true);
    }

    public void hereRadioClicked(View view){

        ZipCode = (EditText) findViewById(R.id.ZipCodeText);
        ZipCode.setEnabled(false);
    }

    /*private StringRequest searchNameStringRequest() {

        String url = "http://ip-api.com/json";

        // 1st param => type of method (GET/PUT/POST/PATCH/etc)
        // 2nd param => complete url of the API
        // 3rd param => Response.Listener -> Success procedure
        // 4th param => Response.ErrorListener -> Error procedure

        return new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        //progressBar.setVisibility(View.INVISIBLE);
                        try {

                            JSONObject result = new JSONObject(response);
                            String zip = result.getString("zip");
                            //Toast.makeText(MainActivity.this , zip.toString(), Toast.LENGTH_LONG).show();
                            Log.d("ZIP: ", zip);
                            parameters.put("zipCodeText", zip);



                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this , e.toString(), Toast.LENGTH_LONG).show();
                        }
                    } // public void onResponse(String response)
                }, // Response.Listener<String>()
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Zip ip-api error", "");
                    }
                });
    }*/



}