package com.ringolatechapps.ringomart;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GRID_ADAPTER_CATEGORIES extends ArrayAdapter<CategoryClass> {


    public GRID_ADAPTER_CATEGORIES(Context context, ArrayList<CategoryClass> UUIDarrayList) {
        super(context, 0, UUIDarrayList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.grid_layout_categories, parent, false);
        }
        CategoryClass mProductClass = getItem(position);  // object that was stored in the arraylist

        ImageView imageView = listitemView.findViewById(R.id.image_view_category);
        TextView text = listitemView.findViewById(R.id.textview_category);

        imageView.setImageDrawable(getContext().getResources().getDrawable(mProductClass.getImg_url()));
        text.setText(mProductClass.getText());

        return listitemView;
    }


}