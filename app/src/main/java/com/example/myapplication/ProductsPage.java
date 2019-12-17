package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.jar.Attributes;

//import static com.example.myapplication.SecondCall.objDetails;
import static com.example.myapplication.SecondCall.messageSecond;
import static com.example.myapplication.SecondCall.context;

public class ProductsPage extends Fragment {
    private static EditText ProductText;
    private RequestQueue queue2;
    private static TextView BrandAns, PriceTop, Shipping, Title, PriceBottom, SpecificationAns, Subtitle;
    private static LinearLayout imagesLayout, highlights1;
    private RequestQueue queue;
    public static JSONObject objDetails = null;
    public static String ViewItemURL;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.prodcut_page, container, false);

        BrandAns = (TextView) view.findViewById(R.id.BrandAns);
        Title = (TextView) view.findViewById(R.id.Title);
        PriceTop = (TextView) view.findViewById(R.id.PriceTop);
        Shipping = (TextView) view.findViewById(R.id.Shipping);
        PriceBottom = (TextView) view.findViewById(R.id.PriceBottomAns);
        SpecificationAns = (TextView) view.findViewById(R.id.SpecificationAns);
        Subtitle = (TextView) view.findViewById(R.id.SubtitleAns);
        imagesLayout = (LinearLayout)view.findViewById(R.id.imagesLayout);
        highlights1 = (LinearLayout)view.findViewById(R.id.highlights1);


        String url1 = "http://angelic-influence.appspot.com/details?itemID=" + messageSecond.get("itemId");

        queue = Volley.newRequestQueue(context);
        JsonObjectRequest getRequest1 = new JsonObjectRequest(Request.Method.GET, url1, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
// display response
                        //Product
                        ProductsPage productObj = new ProductsPage();

                        Log.d("Response", response.toString());
                        try {
                            objDetails = response.getJSONObject("det");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("Response det: ", objDetails.toString());
                        //productObj.renderProductsPage(objDetails, message, context);
                        renderProductsPage(objDetails, messageSecond, context);
                        //Shipping
                        //ShippingPage shippingObj = new ShippingPage();
                        //shippingObj.renderShippingTab(objDetails, messageSecond);

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
        queue.add(getRequest1);

       // Log.d("MyActivity.LOG_TAG", "On tab");

        return view;

    }

    public void renderProductsPage(JSONObject data, HashMap<String, String> message, Context v){
        String brand = "";

        String title = message.get("title");
        String price = "$" + message.get("price");
        String shipping = message.get("shipping");
        String subtitle = "";
        String name = "";
        String value = "";





        if(shipping.equals("Free Shipping")){
            shipping = "With Free Shipping";
        }
        else{
            shipping = "With " + shipping + " Shipping";
        }

        Title.setText(title);
        PriceTop.setText(price);
        Shipping.setText(shipping);
        PriceBottom.setText(price);

        //Log.d("Reached in getDeatils", "In product tab");

        JSONObject obj = null;
        try {
            obj = data.getJSONObject("Item");
            Log.d("Item: ",obj.toString());
        } catch (JSONException e){
            e.printStackTrace();
            Log.d("Item: ", e.toString());
        }

        try{
            ViewItemURL = obj.getString("ViewItemURLForNaturalSearch");

            Log.d("ViewItemURL:",ViewItemURL);

        }catch(JSONException e){
            e.printStackTrace();
            Log.d("Item: ", e.toString());
        }

        JSONArray pictures = null;
        try {
            pictures = obj.getJSONArray("PictureURL");
            Log.d("PictruesURL ", pictures.toString());
        }
        catch(JSONException e){
            e.printStackTrace();
            Log.d("PictruesURL ", e.toString());
        }



        if (obj.has("subtitle")) {
        try {
                subtitle = obj.getString("subtitle");
                Subtitle.setText(subtitle);

            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        else{
            highlights1.setVisibility(View.GONE);
        }

        for (int j = 0; j < pictures.length(); j++) {
            //Log.d("TEST", "ImageURL: " + pictures.get(j));
            ImageView image = new ImageView(v);
            try {
                Picasso.with(image.getContext()).load(String.valueOf(pictures.get(j))).into(image);
                image.setLayoutParams(new android.view.ViewGroup.LayoutParams(1000, ViewGroup.LayoutParams.MATCH_PARENT));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            imagesLayout.addView(image);
        }


        JSONObject obj2 = null;
        try {
            obj2 = obj.getJSONObject("ItemSpecifics").getJSONArray("NameValueList").getJSONObject(0);
            Log.d("NameValueList[0]: ",obj2.toString());
        } catch (JSONException e){
            e.printStackTrace();
            Log.d("NameValueList[0]: ",e.toString());
        }



        try {
            brand = obj2.getString("Value");

            brand = brand.replaceAll("]", "");
            brand = brand.replaceAll("\"", "");
            brand = brand.replaceAll("\\[", "");
            Log.d("Brand: ",brand);
            BrandAns.setText(brand);

        }
        catch(JSONException e){
            e.printStackTrace();
            Log.d("Brand: ",e.toString());
        }

        JSONArray NameValueList = null;
        try {
            NameValueList = obj.getJSONObject("ItemSpecifics").getJSONArray("NameValueList");
            Log.d("NameValueList: ",NameValueList.toString());
        } catch (JSONException e){
            e.printStackTrace();
            Log.d("NameValueList: ",e.toString());
        }

        JSONObject item = null;
        for(int i = 0; i<NameValueList.length(); i++){
            try {
                item = NameValueList.getJSONObject(i);

                value = item.getString("Value");
                value = value.replaceAll("]", "");
                value = value.replaceAll("\"", "");
                value = value.replaceAll("\\[", "");
                name = name + "\n" + value;

            }
            catch(JSONException e){
                e.printStackTrace();
                Log.d("NameValueList: item : ",e.toString());
            }

        }
        Log.d("Specifications:",name);

        SpecificationAns.setText(name);






    }



}