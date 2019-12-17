package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.HashMap;
import java.util.List;

import static com.example.myapplication.FirstCall.KeywordSearch;
import static com.example.myapplication.FirstCall.progress;
import static com.example.myapplication.HomeFragment.searchResults;
import static com.example.myapplication.MainActivity.EXTRA_MESSAGE;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private static List<Model> mDataset;
    private ImageLoader mImageLoader;
    public static HashMap<String, String> parameters2 = new HashMap<>();
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nameText;
        public TextView zip, shipping, wishlist, condition, price;
        public NetworkImageView image;

        @SuppressLint("WrongViewCast")


        public ViewHolder(View v) {
            super(v);

            String s = "Showing " + mDataset.size() + " results for " + KeywordSearch;
            progress.setVisibility(View.GONE);


            searchResults.setText(s);
            nameText = v.findViewById(R.id.title_text);
            zip = v.findViewById(R.id.zip_text);
            shipping = v.findViewById(R.id.shipping);
            //wishlist = v.findViewById(R.id.wishlist);
            condition = v.findViewById(R.id.condition);
            price = v.findViewById(R.id.price);
            image = v.findViewById(R.id.imgAvatar);
            image.setDefaultImageResId(R.mipmap.ic_launcher);




            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();


                    parameters2.put("title", mDataset.get(position).getName());
                    parameters2.put("itemId", mDataset.get(position).getItemId());
                    parameters2.put("shipping", mDataset.get(position).getShipping());
                    parameters2.put("zip", mDataset.get(position).getZip());
                    parameters2.put("price", mDataset.get(position).getPrice());
                    parameters2.put("condition", mDataset.get(position).getCondition());





                    //Toast.makeText(v.getContext(),parameters2.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("ItemID: " , mDataset.get(position).getItemId());
                    Intent intent = new Intent(v.getContext(), SecondCall.class);

                    intent.putExtra(EXTRA_MESSAGE, parameters2);

                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Model> myDataset, Context mCOntext) {
        //Log.d("TEST",myDataset.get(0).getName());
        mDataset = myDataset;
        mImageLoader = MySingleton.getInstance(mCOntext).getImageLoader();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset.get(position).getName());
        //Log.d("TEST","Printing Names onBindView Holder"+mDataset.get(position).getName());
        holder.nameText.setText(mDataset.get(position).getName());
        holder.zip.setText(mDataset.get(position).getZip());
        holder.shipping.setText(mDataset.get(position).getShipping());
        holder.condition.setText(mDataset.get(position).getCondition());
        holder.price.setText(mDataset.get(position).getPrice());
        holder.image.setImageUrl(mDataset.get(position).getImage(),mImageLoader);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}