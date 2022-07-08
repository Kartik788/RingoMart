package com.ringolatechapps.ringomart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GRID_ADAPTER_ACCOUNT extends ArrayAdapter<String> {


    public GRID_ADAPTER_ACCOUNT(Context context, ArrayList<String> UUIDarrayList) {
        super(context, 0, UUIDarrayList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.grid_layout_account, parent, false);
        }
        String options_text = getItem(position);  // object that was stored in the arraylist

        TextView options_textview = listitemView.findViewById(R.id.options_account);


        options_textview.setText(options_text);


        return listitemView;
    }
}