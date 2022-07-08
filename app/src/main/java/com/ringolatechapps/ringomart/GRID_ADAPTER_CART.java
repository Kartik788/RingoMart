package com.ringolatechapps.ringomart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GRID_ADAPTER_CART extends ArrayAdapter<PRODUCT_CLASS> {

    public GRID_ADAPTER_CART(Context context, ArrayList<PRODUCT_CLASS> UUIDarrayList) {
        super(context, 0, UUIDarrayList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listitemView = convertView;


        if (listitemView == null) {

            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.single_list_layout_cart, parent, false);


        }

        PRODUCT_CLASS mProductClass = getItem(position);  // object that was stored in the arraylist


        ImageView imageView = listitemView.findViewById(R.id.image_id);
        TextView Name = listitemView.findViewById(R.id.name_id_cart);
        TextView Price = listitemView.findViewById(R.id.price_id);
        RatingBar Rating = listitemView.findViewById(R.id.rating_id);

        if (mProductClass.getImgUrl() != "not_present") {

            Glide.with(getContext()).load(mProductClass.getImgUrl()).into(imageView);
        }

        Name.setText(mProductClass.getName());
        Price.setText("₹" + mProductClass.getPrice());
        Rating.setRating(Float.valueOf(mProductClass.getRating()));


        return listitemView;
    }


}