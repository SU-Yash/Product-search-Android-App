package com.example.myapplication;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import java.util.Arrays;
import java.util.HashMap;

public class FirstCall extends AppCompatActivity {


    private String zipCode;
    public static HashMap<String, String> Mainmessage;
    FrameLayout frameLayout;
    public static String KeywordSearch;
    public static LinearLayout progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_first);

        Intent intent = getIntent();
        Mainmessage = (HashMap<String, String>) getIntent().getExtras().getSerializable(MainActivity.EXTRA_MESSAGE);
        Log.d("MyActivity.LOG_TAG", String.valueOf(Arrays.asList(Mainmessage)));
        KeywordSearch = Mainmessage.get("Keyword");


        progress = (LinearLayout)findViewById(R.id.removeBar);


        frameLayout = findViewById(R.id.frame);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.frame,new HomeFragment()).commit();

        /*progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);*/





       //Toast.makeText(FirstCall.this ,Mainmessage.get("zipCodeText"), Toast.LENGTH_LONG).show();


    }

    public boolean onOptionsItemSelected(MenuItem item) {

        //handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }


}
