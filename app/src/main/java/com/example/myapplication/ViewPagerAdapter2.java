package com.example.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class ViewPagerAdapter2 extends FragmentPagerAdapter {

    private String title[] = {"Product", "Shipping", "Photos", "Similar"};

    public ViewPagerAdapter2(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0){
            ProductsPage tab1 = new ProductsPage();
            return tab1;
        }
        else if(position == 1){
            ShippingPage tab1 = new ShippingPage();
            return tab1;
        }
        else if(position == 2){
            PhotosPage tab2 = new PhotosPage();
            return tab2;
        }
        else{
            SimilarPage tab3 = new SimilarPage();
            return tab3;
        }
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}