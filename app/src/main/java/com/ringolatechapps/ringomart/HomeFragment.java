package com.ringolatechapps.ringomart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    GridView mGridView;
    ArrayList<PRODUCT_CLASS> PRODUCT_ARRAY;
    ArrayList<String> stack_h;
    ProgressBar mProgressBar;
    EditText search_bar;
    TextView no_Results;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        ((MainActivity)getActivity()).stack
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mGridView = view.findViewById(R.id.grid_view_id);
        mProgressBar = view.findViewById(R.id.progressBar_id);
        search_bar = view.findViewById(R.id.search_id);
        no_Results = view.findViewById(R.id.no_results_id);

        PRODUCT_ARRAY = new ArrayList<PRODUCT_CLASS>();
        stack_h = new ArrayList<String>();

        setAllProducts();

        search_bar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    mProgressBar.setVisibility(View.VISIBLE);
                    ((MainActivity)getActivity()).g=false;
                    close_keyboard();
                    search_bar.clearFocus();
                    performSearch(search_bar.getText().toString());
                    return true;
                }
                return false;
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                send_UUID_productActivity(i);

            }
        });


        return view;
    }

    private void close_keyboard() {
        View view = getActivity().getCurrentFocus();
        if(view != null){
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    public void setAllProducts() {
        no_Results.setVisibility(View.INVISIBLE);
        mGridView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        search_bar.setText("");
        PRODUCT_ARRAY.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("PRODUCTS").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                mProgressBar.setVisibility(View.INVISIBLE);
                mGridView.setVisibility(View.VISIBLE);
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    PRODUCT_CLASS mproduct_obj = new PRODUCT_CLASS(snapshot.getId(), is_null(snapshot), snapshot.get("Name").toString(), snapshot.get("Price").toString());
                    PRODUCT_ARRAY.add(mproduct_obj);
                }
                GRID_ADAPTER mgrid_adapter = new GRID_ADAPTER(getActivity(), PRODUCT_ARRAY);
                mGridView.setAdapter(mgrid_adapter);
            }
        });
    }

    // main search algorithm
    private void setParcticularProducts(String name){
        no_Results.setVisibility(View.INVISIBLE);
        mGridView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        PRODUCT_ARRAY.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("PRODUCTS").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                mProgressBar.setVisibility(View.INVISIBLE);
                mGridView.setVisibility(View.VISIBLE);

                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    if(snapshot.get("Name").toString().toLowerCase().contains(name.toLowerCase())){
                        PRODUCT_CLASS mproduct_obj = new PRODUCT_CLASS(snapshot.getId(), is_null(snapshot), snapshot.get("Name").toString(), snapshot.get("Price").toString());
                        PRODUCT_ARRAY.add(mproduct_obj);
                    }

                }

                if(PRODUCT_ARRAY.size() == 0){
                    no_Results.setVisibility(View.VISIBLE);
                }else{
                    GRID_ADAPTER mgrid_adapter = new GRID_ADAPTER(getActivity(), PRODUCT_ARRAY);
                    mGridView.setAdapter(mgrid_adapter);
                }

            }
        });

    }

    private void performSearch(String name) {
        setParcticularProducts(name);
    }

    private void send_UUID_productActivity(int i) {
        Intent intent = new Intent(getActivity(), ProductActivity.class);
        intent.putExtra("UUID", PRODUCT_ARRAY.get(i).getUUID());
        startActivity(intent);
    }

    private String is_null(DocumentSnapshot documentSnapshot){
        if(documentSnapshot.get("MainImageUrl") != null){
            return documentSnapshot.get("MainImageUrl").toString();
        }
        return "not_present";
    }


}