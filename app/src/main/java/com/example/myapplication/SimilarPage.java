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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SimilarPage extends Fragment {
    public JSONObject objSimilar;
    static JSONArray obj = null;
    private Context context;
    private ImageView imageView;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    Spinner spinnertype;
    Spinner spinnerorder;
    View v;

    static ArrayList similarDataList;
    static ArrayList similarDataListOld;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("SIMILAR CLICKED", "Similar Clicked");

        Log.d("MyActivity.LOG_TAG", "On tab");
        v = inflater.inflate(R.layout.fragment_similar, container, false);
        context = getContext();
        imageView = (ImageView) v.findViewById(R.id.imageView);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        spinnertype = (Spinner)v.findViewById(R.id.typeId);
        spinnerorder = (Spinner)v.findViewById(R.id.orderId);


        gridLayoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        similarDataList = formSimilarItemData();
        similarDataListOld = similarDataList;

        final DataAdapterSimilar dataAdapter = new DataAdapterSimilar(context, similarDataList);
        recyclerView.setAdapter(dataAdapter);

        spinnertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String oderValue = spinnerorder.getSelectedItem().toString();
                String typeValue = spinnertype.getSelectedItem().toString();
                changetheAdapter(oderValue, typeValue);
                switch (position) {
                    case 0:
                        //Toast.makeText(parent.getContext(), "Default", Toast.LENGTH_SHORT).show();
                        similarDataList = similarDataListOld;
                        break;
                    case 1:
                        //Toast.makeText(parent.getContext(), "Name", Toast.LENGTH_SHORT).show();

                        break;
                    case 2:
                        // Toast.makeText(parent.getContext(), "Price", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        // Toast.makeText(parent.getContext(), "Days", Toast.LENGTH_SHORT).show();
                        break;
                }
                dataAdapter.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerorder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String oderValue = spinnerorder.getSelectedItem().toString();
                String typeValue = spinnertype.getSelectedItem().toString();
                changetheAdapter(oderValue, typeValue);
                switch (position) {
                    case 0:
                        // Toast.makeText(parent.getContext(), "Ascending", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        //Toast.makeText(parent.getContext(), "Descending", Toast.LENGTH_SHORT).show();
                        break;
                }

                dataAdapter.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    return v;

    }
    public static void changetheAdapter(String order, String type){

        if(order.equals("Ascending")){
            switch (type){
                case "Default":
                    //similarDataList = similarDataListOld;
                    break;
                case "Name":
                    Collections.sort(similarDataList, new Comparator<SimilarItemData>() {
                        @Override
                        public int compare(SimilarItemData o1, SimilarItemData o2) {
                            return o1.getTitle().compareToIgnoreCase(o2.getTitle());
                        }
                    });
                    break;
                case "Price":
                    Collections.sort(similarDataList, new Comparator<SimilarItemData>() {
                        @Override
                        public int compare(SimilarItemData o1, SimilarItemData o2) {
                            return Double.valueOf(o1.getPrice().substring(1, o1.getPrice().length())).compareTo(Double.valueOf(o2.getPrice().substring(1, o2.getPrice().length())));
                        }
                    });
                    break;
                case "Days":
                    Collections.sort(similarDataList, new Comparator<SimilarItemData>() {
                        @Override
                        public int compare(SimilarItemData o1, SimilarItemData o2) {
                            return Integer.valueOf(o1.getDaysleft()).compareTo(Integer.valueOf(o2.getDaysleft()));
                        }
                    });
                    break;

            }
        }
        else{

            switch (type){
                case "Default":
                    similarDataList = similarDataListOld;
                    break;
                case "Name":
                    Collections.sort(similarDataList, new Comparator<SimilarItemData>() {
                        @Override
                        public int compare(SimilarItemData o1, SimilarItemData o2) {
                            return o2.getTitle().compareToIgnoreCase(o1.getTitle());
                        }
                    });
                    break;
                case "Price":
                    Collections.sort(similarDataList, new Comparator<SimilarItemData>() {
                        @Override
                        public int compare(SimilarItemData o1, SimilarItemData o2) {
                            return Double.valueOf(o2.getPrice().substring(1, o2.getPrice().length())).compareTo(Double.valueOf(o1.getPrice().substring(1, o1.getPrice().length())));
                        }
                    });
                    break;
                case "Days":
                    Collections.sort(similarDataList, new Comparator<SimilarItemData>() {
                        @Override
                        public int compare(SimilarItemData o1, SimilarItemData o2) {
                            return Integer.valueOf(o2.getDaysleft()).compareTo(Integer.valueOf(o1.getDaysleft()));
                        }
                    });
                    break;

            }

        }

    }

    private ArrayList formSimilarItemData() {
        Log.d("MainActivity", "List count: " + obj.length());

        ArrayList similarItem = new ArrayList<>();
        for (int i = 0; i < obj.length(); i++) {
            try {
                JSONObject eachObject = (JSONObject) obj.get(i);
                SimilarItemData similarDataEach = new SimilarItemData();
                similarDataEach.setImageUrl(eachObject.getString("image"));
                similarDataEach.setTitle(eachObject.getString("title"));
                similarDataEach.setPrice(eachObject.getString("price"));
                similarDataEach.setShipping(eachObject.getString("shippingCost"));
                similarDataEach.setDaysLeft(eachObject.getString("timeLeft"));
                similarItem.add(similarDataEach);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        for(int j=0; j<similarItem.size(); j++){
            Log.d("MainActivity", "Each Item in list" + similarItem.get(j));
        }
        Log.d("MainActivity", "List count: "+similarItem.size());
        return similarItem;
    }


    public void renderSimilarPage(JSONObject response){
        /*String body = "";
        JSONArray item = null;
        String ImageURL = "";
        String title = "";
        JSONObject buyItNowPrice = null;
        JSONObject shippingCost = null;
        String buyItNowPriceString = "";
        String shippingCostString = "";

        Log.d("Reached in SimilarPage", "In Simiar Tab");
        Log.d("Sim Items", data.toString());
        try {
            body= data.getString("body");
           // objSimilar = objSimilar.getJSONObject("getSimilarItemsResponse");
            objSimilar = new JSONObject(body).getJSONObject("getSimilarItemsResponse").getJSONObject("itemRecommendations");




            Log.d("Sim Items objSImilar", objSimilar.toString());
            item = objSimilar.getJSONArray("item");
            for(int i =0; i< item.length(); i++) {
                JSONObject currentItem = item.getJSONObject(i);
               // Log.d("Similar Items " + i, item.getJSONObject(i).toString());

                if(currentItem.has("imageURL")){
                    ImageURL = currentItem.getString("imageURL");
                }

                if(currentItem.has("title")){
                    title = currentItem.getString("title");
                }

                if(currentItem.has("buyItNowPrice")){
                    buyItNowPrice = currentItem.getJSONObject("buyItNowPrice");
                    buyItNowPriceString = buyItNowPrice.getString("__value__");
                }

                if(currentItem.has("shippingCost")){
                    shippingCost = currentItem.getJSONObject("shippingCost");
                    shippingCostString = shippingCost.getString("__value__");
                }

                Log.d("imageURL", ImageURL);
                Log.d("title", title);
                Log.d("buyItNowPrice", buyItNowPriceString);
                Log.d("shippingCost", shippingCostString);

            }

        }
        catch(JSONException e){
            e.printStackTrace();
            Log.d("Error: ", e.toString());
        }*/
        Log.d("MyActivity.LOG_TAG", " Similar Response: "+response.toString());
        try {
            obj = response.getJSONArray("data");
            for(int i=0; i<obj.length(); i++){
                Log.d("MyActivity.LOG_TAG", "Similar Item: "+obj.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



}