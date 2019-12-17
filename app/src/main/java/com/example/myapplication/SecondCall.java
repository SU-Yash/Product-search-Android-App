package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

import static com.example.myapplication.ProductsPage.ViewItemURL;


public class SecondCall extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RequestQueue queue;
    public ProductsPage productObj;
    public ShippingPage shippingObj;
    public SimilarPage similarObj;
    public static HashMap<String, String> messageSecond;
    public HashMap<String, String> ShippingMessage = new HashMap<>();
    public static JSONObject objDetails = null, objPhotos = null, objSimilar = null;
    public static Context context;


    private int[] tabIcons = {
            R.drawable.ic_information_variant,
            R.drawable.ic_truck_delivery,
            R.drawable.ic_google,
            R.drawable.ic_equal,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_second);
        Intent intent = getIntent();



        context = this;
        messageSecond = (HashMap<String, String>) getIntent().getExtras().getSerializable(MainActivity.EXTRA_MESSAGE);
        Log.d("MyActivity.LOG_TAG", String.valueOf(Arrays.asList(messageSecond)));
        //Toast.makeText(SecondCall.this ,, Toast.LENGTH_LONG).show();
        setTitle(messageSecond.get("title"));



        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter2 adapter = new ViewPagerAdapter2(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();

        DetailsTab();

    }


    public void DetailsTab(){

        String url3 = "https://angular2502.appspot.com/viewSimilarItemNew?itemId=" + messageSecond.get("itemId");
        queue = Volley.newRequestQueue(this);
        JsonObjectRequest getRequest3 = new JsonObjectRequest(Request.Method.GET, url3, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
// display response
                        /*//String ans, link1;
                        Log.d("Response Simialr", "in Similar");
                        try {
                            objSimilar = response.getJSONObject("det");
                            // Log.d("Response Photos det: ", ans);
                            //objSimilar = new JSONObject(ans);

                            //link1 = objPhotos.getString("link");

                             Log.d("Similar Items ", objSimilar.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Error: ", e.toString());
                        }*/



                        //Similar
                        SimilarPage similarObj = new SimilarPage();
                        similarObj.renderSimilarPage(response);


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", "error");
                    }
                }
        );

        queue.add(getRequest3);


    }




    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.facebook, menu);

        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        //handle presses on the action bar items
        switch (item.getItemId()) {

            case R.id.facebook_icon:
                String msg = "Buy "+ messageSecond.get("title")+" at "+messageSecond.get("price") +" from Ebay!";
                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.facebook.com/dialog/share?app_id=2282148341844093&display=popup&href="+ ViewItemURL+"&quote="+msg+"&hashtag=%27%23CSCI571Spring2019Ebay%27"));
                startActivity(i);
                return true;

            case android.R.id.home:
                finish();

        }
        return true;
    }
}
