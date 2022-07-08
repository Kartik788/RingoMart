package com.ringolatechapps.ringomart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CategoriesFragment extends Fragment {

    GridView gridView;
    ArrayList<CategoryClass> arrayList = new ArrayList<>();
    String[] text_names = {"Electronics","Books","Sports", "Furniture", "Grocery","Toys and Baby", "Home", "Clothes","Beauty", "Appliances","Mobiles"};
    int[] images = {R.drawable.electronics,R.drawable.books,R.drawable.sports,R.drawable.furniture,R.drawable.grocery,R.drawable.toys,R.drawable.home,R.drawable.clothes,R.drawable.beauty,R.drawable.appliances,R.drawable.mobiles};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        gridView = view.findViewById(R.id.grid_view_category);


        for (int i=0;i<text_names.length;i++) {
            CategoryClass categoryClass = new CategoryClass(images[i],text_names[i]);
            arrayList.add(categoryClass);
        }

        GRID_ADAPTER_CATEGORIES grid_adapter_categories = new GRID_ADAPTER_CATEGORIES(getContext(), arrayList);
        gridView.setAdapter(grid_adapter_categories);
        return view;
    }
}