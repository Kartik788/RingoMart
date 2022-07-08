package com.ringolatechapps.ringomart;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class GRID_ADAPTER extends ArrayAdapter<PRODUCT_CLASS> {


    public GRID_ADAPTER(Context context, ArrayList<PRODUCT_CLASS> UUIDarrayList) {
        super(context, 0, UUIDarrayList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.single_grid_layout, parent, false);
        }
        PRODUCT_CLASS mProductClass = getItem(position);  // object that was stored in the arraylist

        ImageView imageView = listitemView.findViewById(R.id.imageView_id);
        TextView Name = listitemView.findViewById(R.id.Name_id);
        TextView Price = listitemView.findViewById(R.id.Price_id);
        ProgressBar progressBar = listitemView.findViewById(R.id.progress_id);

        if (mProductClass.getImgUrl() != "not_present") {

            Glide.with(getContext()).load(mProductClass.getImgUrl()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    progressBar.setVisibility(View.INVISIBLE);
                    return false;
                }
            }).into(imageView);
        }

        Name.setText(mProductClass.getName());
        Price.setText("₹" + mProductClass.getPrice());

        return listitemView;
    }


}
