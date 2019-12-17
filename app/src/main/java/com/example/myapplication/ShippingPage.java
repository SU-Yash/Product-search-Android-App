package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.telecom.StatusHints;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wssholmes.stark.circular_score.CircularScoreView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

import static com.example.myapplication.SecondCall.context;
import static com.example.myapplication.SecondCall.messageSecond;

public class ShippingPage extends Fragment {

    private static TextView StoreName, FeedScore, ShippingC, HandlingT, GlobalS, Condi, Policy, ReturnsWithin, RefundMode, ShippedBy;
    private static ImageView FeedBackStar;
    private static LinearLayout return1, return2, return3, return4, store1, store2, store3, store4, shipping1, shipping2, shipping3, shipping4;
    private static  CircularScoreView Popularity;
    public static String StoreURL = "";
    public static String StoreU = "";
    public static JSONObject objDetails = null;
    private RequestQueue queue;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("MyActivity.LOG_TAG", "On tab");
        //Log.d("IN Shipping Tab: ", obj.toString());
        View view = inflater.inflate(R.layout.shipping_page, container, false);

        StoreName = (TextView) view.findViewById(R.id.StoreNameAns);
        FeedScore  = (TextView) view.findViewById(R.id.FeedBackScoreAns);
        Popularity = (CircularScoreView)view.findViewById(R.id.PopularityAns);
        FeedBackStar = (ImageView)view.findViewById(R.id.FeedBackStarAns);
        ShippingC = (TextView)view.findViewById(R.id.ShippingCostAns);
        HandlingT = (TextView)view.findViewById(R.id.HandlingTimeAns);
        GlobalS = (TextView)view.findViewById(R.id.GlobalShippingAns);
        Condi = (TextView)view.findViewById(R.id.ConditionAns);
        Policy = (TextView)view.findViewById(R.id.PolicyAns);
        ReturnsWithin = (TextView)view.findViewById(R.id.ReturnsWithinAns);
        RefundMode = (TextView)view.findViewById(R.id.RefundModeAns);
        ShippedBy =(TextView)view.findViewById(R.id.ShippedByAns);

        return1 = (LinearLayout)view.findViewById(R.id.Return1);
        return2 = (LinearLayout)view.findViewById(R.id.Return2);
        return3 = (LinearLayout)view.findViewById(R.id.Return3);
        return4 = (LinearLayout)view.findViewById(R.id.Return4);

        store1 = (LinearLayout)view.findViewById(R.id.Store1);
        store2 = (LinearLayout)view.findViewById(R.id.Store2);
        store3 = (LinearLayout)view.findViewById(R.id.Store3);
        store4 = (LinearLayout)view.findViewById(R.id.Store4);

        shipping1 = (LinearLayout)view.findViewById(R.id.Shipping1);
        shipping2 = (LinearLayout)view.findViewById(R.id.Shipping2);
        shipping3 = (LinearLayout)view.findViewById(R.id.Shipping3);
        shipping4 = (LinearLayout)view.findViewById(R.id.Shipping4);

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
                        //renderShippingTab(objDetails, messageSecond, context);
                        //Shipping
                        //ShippingPage shippingObj = new ShippingPage();
                        renderShippingTab(objDetails, messageSecond);

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


        return view;


    }

    public void renderShippingTab(JSONObject obj1, HashMap<String,String> message2) {
        JSONObject seller = null;

        String FeedBackRatingStar = "";
        String FeedBackScore = "";
        String PositiveFeedBackPercent = "";
        String ShippingCost = message2.get("shipping");

        String GlobalShipping = "";
        String HandlingTime = "";
        String Condition = message2.get("condition");

        String returns = "";
        String returnsWithin = "";
        String refund = "";
        String shippingCostPaidBy = "";




        Log.d("Reached in shippingTab", "In shipping tab");
        Log.d("IN Shipping Tab: ", obj1.toString());
        Log.d("IN Shipping Tab: ", message2.toString());



       /* JSONObject obj = null;
        try {
            obj = data.getJSONObject("Item");
            Log.d("Item: ",obj.toString());
        } catch (JSONException e){
            e.printStackTrace();
            Log.d("Item: ", e.toString());
        }*/
        JSONObject item = null;
        try {
            if(obj1.has("Item")) {
                item = obj1.getJSONObject("Item");
            }
            else{
                item = null;
            }
            Log.d("Item: ", item.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("Item: ", e.toString());
        }



        try {
            if(item.has("Seller")) {
                seller = item.getJSONObject("Seller");
            }
            else{
                seller = null;
            }

            //Sold by
            if(item.has("Storefront")){
                if(item.getJSONObject("Storefront").has("StoreName")){
                    StoreURL = item.getJSONObject("Storefront").getString("StoreName");
                    StoreName.setText(StoreURL);
                    StoreName.setPaintFlags(StoreName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    if(item.getJSONObject("Storefront").has("StoreURL")){
                        StoreU = item.getJSONObject("Storefront").getString("StoreURL");
                    }
                }

                else{
                    store1.setVisibility(View.GONE);
                }
            }
            else{
                store1.setVisibility(View.GONE);
            }

            StoreName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(v.getContext(), "Hello", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(StoreU));
                    Context c = v.getContext();
                    c.startActivity(i);
                }
            });


            if(seller.has("FeedbackRatingStar")) {
                FeedBackRatingStar = seller.getString("FeedbackRatingStar");
                //FeedBackStar.setText(FeedBackRatingStar);
            }
            else{
               store2.setVisibility(View.GONE);
            }



            if(seller.has("FeedbackScore")){
                FeedBackScore = seller.getString("FeedbackScore");
                FeedScore.setText(FeedBackScore);
            }
            else{
                store3.setVisibility(View.GONE);
            }


            if(seller.has("FeedbackScore") && seller.has("FeedbackRatingStar")) {
                int val = Integer.parseInt(FeedBackScore);
                if (val < 10000)
                    FeedBackStar.setImageResource(R.drawable.ic_star_circle_outline);

                else {
                    FeedBackStar.setImageResource(R.drawable.ic_star_circle);
                    int shootIndex = FeedBackRatingStar.indexOf("Shooting");
                    FeedBackRatingStar = FeedBackRatingStar.substring(0, shootIndex);
                }

                switch (FeedBackRatingStar) {
                    case "Blue":
                        FeedBackStar.setColorFilter(Color.BLUE);
                        break;
                    case "Green":
                        FeedBackStar.setColorFilter(Color.GREEN);
                        break;
                    case "Purple":
                        FeedBackStar.setColorFilter(0x6F00F8);
                        break;
                    case "Red":
                        FeedBackStar.setColorFilter(Color.RED);
                        break;
                    case "Yellow":
                        FeedBackStar.setColorFilter(Color.YELLOW);
                        break;
                    case "Turquoise":
                        FeedBackStar.setColorFilter(Color.CYAN);
                    case "Silver":
                        FeedBackStar.setColorFilter(0xC0C0C0);
                        break;

                }
            }

            if(seller.has("PositiveFeedbackPercent")){
                PositiveFeedBackPercent = seller.getString("PositiveFeedbackPercent");
                Float score = Float.parseFloat(PositiveFeedBackPercent);
                int scoreInt = Math.round(score);
                Popularity.setScore(scoreInt);
            }
            else{
                store4.setVisibility(View.GONE);
            }


            Log.d("Seller: ", seller.toString());
            Log.d("StoreURL: ", StoreURL);
            Log.d("FeedBackRatingStar: ", FeedBackRatingStar);
            Log.d("FeedBackScore: ", FeedBackScore);
            Log.d("PositiveFeedBackPe:", PositiveFeedBackPercent);

            // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


            /* ShippingMessage.put("FeedBackRatingStar", FeedBackRatingStar);
            ShippingMessage.put("FeedBackScore", FeedBackScore);
            ShippingMessage.put("PositivePercent", PositiveFeedBackPercent);

            shippingObj.renderShippingTab(ShippingMessage);*/



            //Shipping Info


            if(item.has("GlobalShipping")){
                GlobalShipping = item.getString("GlobalShipping");
                if(GlobalShipping == "false"){
                    GlobalShipping = "No";
                }
                else{
                    GlobalShipping = "Yes";
                }
                GlobalS.setText(GlobalShipping);

            }
            else{
                shipping3.setVisibility(View.GONE);
            }

            if(item.has("HandlingTime")){
                HandlingTime = item.getString("HandlingTime");
                if(HandlingTime.equals("0") || HandlingTime.equals("1")){
                    HandlingTime = HandlingTime + " day";
                }
                else{
                    HandlingTime = HandlingTime + " days";
                }
                HandlingT.setText(HandlingTime);

            }
            else{
                shipping2.setVisibility(View.GONE);
            }


            Log.d("ShippingCost: ", ShippingCost);
            Log.d("GlobalShipping ", GlobalShipping);
            Log.d("Handling ", HandlingTime);
            Log.d("Condition ", Condition);

            ShippingC.setText(ShippingCost);
            Condi.setText(Condition);





            //Return Policy

            if(item.has("ReturnPolicy")){
                if(item.getJSONObject("ReturnPolicy").has("ReturnsAccepted")) {
                    returns = item.getJSONObject("ReturnPolicy").getString("ReturnsAccepted");
                    Policy.setText(returns);
                }
                else{
                    return1.setVisibility(View.GONE);
                }

                if(item.getJSONObject("ReturnPolicy").has("ReturnsWithin")) {
                    returnsWithin = item.getJSONObject("ReturnPolicy").getString("ReturnsWithin");
                    ReturnsWithin.setText(returnsWithin);
                }
                else{
                    return2.setVisibility(View.GONE);
                }


                if(item.getJSONObject("ReturnPolicy").has("ReturnsWithin")) {
                    refund = item.getJSONObject("ReturnPolicy").getString("Refund");
                    RefundMode.setText(refund);
                }
                else{
                    return3.setVisibility(View.GONE);
                }


                if(item.getJSONObject("ReturnPolicy").has("ShippingCostPaidBy")) {
                    shippingCostPaidBy = item.getJSONObject("ReturnPolicy").getString("ShippingCostPaidBy");
                    ShippedBy.setText(shippingCostPaidBy);
                }
                else{
                    return4.setVisibility(View.GONE);
                }

            }




            Log.d("Returns Accepted", returns);
            Log.d("ReturnsWithin", returnsWithin);
            Log.d("Refund", refund);
            Log.d("ShippingCostPaidBy", shippingCostPaidBy);




        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("Seller: ", e.toString());

        }

        StoreName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(),"Hello", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(StoreU));
                Context c = v.getContext();
                c.startActivity(i);
            }
        });

    }


}