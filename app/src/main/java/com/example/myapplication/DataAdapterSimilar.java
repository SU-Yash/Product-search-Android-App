package com.example.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DataAdapterSimilar extends RecyclerView.Adapter<DataAdapterSimilar.ViewHolder> {
    private ArrayList<SimilarItemData> imageUrls;
    private Context context;

    public DataAdapterSimilar(Context context, ArrayList<SimilarItemData> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_layout_similar, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Picasso.with(context).load(imageUrls.get(i).getImageUrl()).into(viewHolder.img);
        viewHolder.similarTitle.setText(imageUrls.get(i).getTitle());
        viewHolder.similarPrice.setText(imageUrls.get(i).getPrice());
        viewHolder.similarShipping.setText(imageUrls.get(i).getShipping());
        viewHolder.similarDaysleft.setText(imageUrls.get(i).getDaysleft());
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView similarTitle;
        TextView similarPrice;
        TextView similarShipping;
        TextView similarDaysleft;
        Spinner spinnertype;

        public ViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.imageView);
            similarTitle = view.findViewById(R.id.similarTitle);
            similarPrice = view.findViewById(R.id.similarPrice);
            similarShipping = view.findViewById(R.id.similarShipping);
            similarDaysleft = view.findViewById(R.id.similarDaysLeft);
            spinnertype = view.findViewById(R.id.typeId);

        }
    }
}