package com.ringolatechapps.ringomart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.Holder> {

    private List<String> mSliderItems = new ArrayList<>();
    Context context;

    public SliderAdapterExample(Context context, List<String> mSliderItems) {
        this.context = context;
        this.mSliderItems = mSliderItems;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {

        // here the image slider gets the images
        Glide.with(context).load(mSliderItems.get(position)).into(viewHolder.imageViewBackground);

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class Holder extends SliderViewAdapter.ViewHolder {

        ImageView imageViewBackground;


        public Holder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.image_background_id);
        }
    }

}
