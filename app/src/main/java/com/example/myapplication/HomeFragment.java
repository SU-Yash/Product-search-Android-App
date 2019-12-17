package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;
import static com.example.myapplication.FirstCall.Mainmessage;
import static com.example.myapplication.FirstCall.progress;
import static com.example.myapplication.MainActivity.zipGlobal;

public class HomeFragment extends Fragment implements Response.Listener , Response.ErrorListener {

    View view;
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context context;
    private List<Model> contactList = new ArrayList<Model>();
    private String url; //"http://angelic-influence.appspot.com//submit?Keyword=iphone&Distance=10&zip=90007";
    private ProgressBar progressBar;
    public static TextView searchResults;
    public String s;
    public TextView error;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);

        searchResults = (TextView)view.findViewById(R.id.SearchResults);

        context = getContext();
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        mLayoutManager = new GridLayoutManager(context, 2);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Log.d("In Home Fragment:" , FirstCall.Mainmessage.toString());
        Log.d("IN Home Fragment", Mainmessage.get("zip"));

        url = "http://angelic-influence.appspot.com//submit?Keyword=" + Mainmessage.get("Keyword") +"&Categories="+ Mainmessage.get("Category")  + "&Distance="+ Mainmessage.get("Miles") +"&zip=" + Mainmessage.get("zip") +"&New="+ Mainmessage.get("New") + "&Used=" + Mainmessage.get("Used") + "&Unspecified=" + Mainmessage.get("Unspecified") + "&FreeShipping=" + Mainmessage.get("Free") + "&LocalShipping=" + Mainmessage.get("Local");

        Log.d("URL",url);
        //Log.d("zip", FirstCall.Mainmessage.get("zip"));
        //Log.d("dist", FirstCall.Mainmessage.get("MilesFrom"));
        getContactList();

          return view;
    }

    private void getContactList() {
        Log.d("TEST", "Getting Contact list");
        Controller.getInstance(context).makeNetworkCalls(Request.Method.GET, url, this, this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, "Error: " + error.getMessage());
        Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Object response) {



        Log.d("TEST", "Got The response" + response);
        //progressBar.setVisibility(View.VISIBLE);
        if (response == null) {
            Toast.makeText(context, "Couldn't fetch the contacts! Pleas try again.", Toast.LENGTH_LONG).show();
            return;
        }
        Log.d(TAG, "In success " + response);


        JSONObject obj = null;
        try {
            obj = new JSONObject((String) response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("TEST", "Object: " + obj);

        JSONObject det = null;
        JSONArray searchRe = null;

        try {
            det = obj.getJSONObject("det").getJSONArray("findItemsAdvancedResponse").getJSONObject(0);
            s = det.getString("ack");

            s = s.replaceAll("]", "");
            s = s.replaceAll("\"", "");
            s = s.replaceAll("\\[", "");

            Log.d("JSONARRAY", s);
            if(s.equals("Failure")){
                Log.d("ERROR", "first if");
                progress.setVisibility(View.GONE);
                error = (TextView)view.findViewById(R.id.Error);
                error.setVisibility(View.VISIBLE);

                return;
            }
            searchRe = det.getJSONArray("searchResult");
            if(!det.has("searchResult") || (!searchRe.getJSONObject(0).has("item"))){
                progress.setVisibility(View.GONE);
                Log.d("ERROR", "second if");
                error = (TextView)view.findViewById(R.id.Error);
                error.setVisibility(View.VISIBLE);

                return;
            }

        }
        catch(JSONException e){
            e.printStackTrace();
        }



            Log.d("NO ERROR", "");

            JSONArray item = null;
            try {
                item = obj.getJSONObject("det").getJSONArray("findItemsAdvancedResponse").getJSONObject(0).getJSONArray("searchResult").getJSONObject(0).getJSONArray("item");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("TEST", "item: " + item.length());

            JSONArray newarray = new JSONArray();

            for (int i = 0; i < item.length(); i++) {
                try {
                    JSONObject itemObject = item.getJSONObject(i);
                    String image = String.valueOf(itemObject.getJSONArray("galleryURL"));
                    String title = String.valueOf(itemObject.getJSONArray("title"));
                    String zip = String.valueOf(itemObject.getJSONArray("postalCode"));
                    String itemId = String.valueOf(itemObject.getJSONArray("itemId"));

                    String price = String.valueOf(itemObject.getJSONArray("sellingStatus").getJSONObject(0).getJSONArray("currentPrice").getJSONObject(0).getString("__value__"));


                    String shipping = (itemObject.getJSONArray("shippingInfo").getJSONObject(0).getJSONArray("shippingServiceCost").getJSONObject(0).getString("__value__"));
                    if (shipping.equals("0.0")) {
                        shipping = "Free Shipping";
                    }

                    String condition = String.valueOf(itemObject.getJSONArray("condition").getJSONObject(0).getJSONArray("conditionDisplayName"));


                    image = image.replaceAll("]", "");
                    image = image.replaceAll("\"", "");
                    image = image.replaceAll("\\[", "");


                    title = title.replaceAll("]", "");
                    title = title.replaceAll("\"", "");
                    title = title.replaceAll("\\[", "");

                    zip = zip.replaceAll("]", "");
                    zip = zip.replaceAll("\"", "");
                    zip = zip.replaceAll("\\[", "");

                    condition = condition.replaceAll("]", "");
                    condition = condition.replaceAll("\"", "");
                    condition = condition.replaceAll("\\[", "");

                    itemId = itemId.replaceAll("]", "");
                    itemId = itemId.replaceAll("\"", "");
                    itemId = itemId.replaceAll("\\[", "");

               /* Log.d("TEST", "image: " + image);
                Log.d("TEST", "title: " + title);
                Log.d("TEST", "image: " + zip);
                Log.d("TEST", "shipping: " + shipping);
                Log.d("TEST", "condition: " + condition);
                Log.d("TEST", "itemid: " + itemId);
                Log.d("TEST", "price: " + price);*/


                    JSONObject newobject = new JSONObject();
                    newobject.put("name", title);
                    newobject.put("image", image);
                    newobject.put("zip", zip);
                    newobject.put("shipping", shipping);
                    newobject.put("price", price);


                    if(condition.equals("Manufacturer refurbished") || condition.equals("Seller refurbished")){
                        newobject.put("condition", "Refurbished");
                    }
                    else{
                        newobject.put("condition", condition);
                    }

                    newobject.put("itemId", itemId);
                    newarray.put(newobject);

                /*String shipping = itemObject.getString("shippingType");
                String price = itemObject.getString("price");
                String condition = itemObject.getString("condition");
                String itemId = itemObject.getString("itemId");*/

                } catch (JSONException e) {
                    e.printStackTrace();

                }


            }

            Log.d("TEST", "Got The New array " + newarray);

            List<Model> items = new Gson().fromJson(newarray.toString(), new TypeToken<List<Model>>() {
            }.getType());
            contactList.addAll(items);
            MyAdapter rcAdapter = new MyAdapter(contactList, context);
            mRecyclerView.setAdapter(rcAdapter);
        }


    }

