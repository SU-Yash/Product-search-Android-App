package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import static com.example.myapplication.SecondCall.context;
import static com.example.myapplication.SecondCall.messageSecond;
import java.util.HashMap;

public class PhotosPage extends Fragment {
    public String link1;
    private Context context;
    private ImageView imageView;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    public static JSONObject response= null;
    static String imageUrls[];
    RequestQueue queue;
    private TextView error2;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("MyActivity.LOG_TAG", "On tab");
        View v = inflater.inflate(R.layout.fragment_photos, container, false);
        context = getContext();
        imageView = (ImageView) v.findViewById(R.id.imageView);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        gridLayoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        error2 = (TextView)v.findViewById(R.id.ErrorPhotos);

        String url2 = "https://angular2502.appspot.com/photoAPI?itemTitle=="+ messageSecond.get("title");

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url2, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("Url in  Tab Photo", url2);
                        Log.d("Response in  Tab Photo", response.toString());
                        try {
                            if(!response.getString("data").equals("No Photos")) {
                                renderPhotoTab(response);
                                ArrayList imageUrlList;
                                imageUrlList = prepareData();
                                DataAdapter dataAdapter = new DataAdapter(context, imageUrlList);
                                recyclerView.setAdapter(dataAdapter);
                            }
                            else{
                                error2.setVisibility(View.VISIBLE);
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
        queue.add(getRequest);

        return v;
    }

    private ArrayList prepareData() {

        ArrayList imageUrlList = new ArrayList<>();
        for (int i = 0; i < imageUrls.length; i++) {
            ImageUrl imageUrl = new ImageUrl();
            imageUrl.setImageUrl(imageUrls[i]);
            imageUrlList.add(imageUrl);
        }

        return imageUrlList;
    }

    public void renderPhotoTab(JSONObject data) {
        /*JSONArray items = null;
        //Log.d("In photos Tab", data111.toString());
        try {
            items = data111.getJSONArray("items");
            link1 = items.getJSONObject(0).getString("link");
            Log.d("objPhotos in Photos Tab", link1);

        }
        catch(JSONException e){
            e.printStackTrace();
            Log.d("Error: ", e.toString());
        }*/

        JSONArray obj = null;
        try {
            obj = data.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(obj!=null) {
            imageUrls = new String[obj.length() - 1];
            for (int i = 0; i < obj.length() - 1; i++) {
                try {
                    imageUrls[i] = (String) obj.get(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}