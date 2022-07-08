package com.ringolatechapps.ringomart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyListAdapter extends ArrayAdapter<String> {


    ArrayList<PRODUCT_CLASS> arrayList;
    Context context;

    public MyListAdapter(Context context, ArrayList<PRODUCT_CLASS> arrayList) {
        super(context, R.layout.single_list_layout_cart);

        this.arrayList = arrayList;
        this.context = context;
    }

    public View getView(int position, View view, ViewGroup parent) {

        PRODUCT_CLASS product_class = arrayList.get(position);

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.single_list_layout_cart, null);
        }

        TextView nameText = (TextView) view.findViewById(R.id.name_id_cart);
        TextView ratingText = (TextView) view.findViewById(R.id.rating_id);
        TextView priceText = (TextView) view.findViewById(R.id.price_id);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_id);

        nameText.setText(product_class.getName());
        ratingText.setText(product_class.getRating());
        priceText.setText(product_class.getPrice());
//        Glide.with(getContext()).load(product_class.getImgUrl()).into(imageView);

        return view;

    }

    ;
}