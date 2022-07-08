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

public class GRID_ADAPTER_WISHLIST extends ArrayAdapter<PRODUCT_CLASS> {


    public GRID_ADAPTER_WISHLIST(Context context, ArrayList<PRODUCT_CLASS> UUIDarrayList) {
        super(context, 0, UUIDarrayList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.grid_layout_wishlist, parent, false);
        }
        PRODUCT_CLASS mProductClass = getItem(position);  // object that was stored in the arraylist

        ImageView imageView = listitemView.findViewById(R.id.image_id_wishlist);
        TextView Name = listitemView.findViewById(R.id.name_id_wishlist);
        TextView Price = listitemView.findViewById(R.id.price_id_wishlist);
        RatingBar Rating = listitemView.findViewById(R.id.rating_id_wishlist);

        if (mProductClass.getImgUrl() != "not_present") {

            Glide.with(getContext()).load(mProductClass.getImgUrl()).into(imageView);
        }

        Name.setText(mProductClass.getName());
        Price.setText("₹" + mProductClass.getPrice());
        Rating.setRating(Float.valueOf(mProductClass.getRating()));

        return listitemView;
    }


}
