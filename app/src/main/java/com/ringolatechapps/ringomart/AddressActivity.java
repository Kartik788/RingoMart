package com.ringolatechapps.ringomart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<ADDRESS> arrayList;
    Toolbar add_new_address;
    String UUID;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        UUID = getIntent().getStringExtra("UUID");
        gridView = findViewById(R.id.grid_view_address);
        progressBar = findViewById(R.id.address_loader);
        add_new_address = findViewById(R.id.toolbar7);
        arrayList = new ArrayList<ADDRESS>();
        add_new_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(AddressActivity.this,AddAddress.class);
                in.putExtra("WORK","NEW_ADDRESS");
                in.putExtra("POSITION","-1");
                startActivity(in);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        arrayList.clear();
        progressBar.setVisibility(View.VISIBLE);
        setAllAddress(UUID);
    }

    private void setAllAddress(String uuid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("USERS").document(uuid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ArrayList<HashMap> address_list = (ArrayList<HashMap>)documentSnapshot.get("Address");
                for(HashMap<String,String> m : address_list){
                    ADDRESS address = new ADDRESS(m.get("AddressLine_1"),m.get("AddressLine_2"),m.get("City"),m.get("FullName"),m.get("Landmark"),m.get("Number"),m.get("Pincode"),m.get("State"));
                    arrayList.add(address);
                }
                progressBar.setVisibility(View.GONE);
                GRID_ADAPTER_ADDRESS grid_adapter_address = new GRID_ADAPTER_ADDRESS(AddressActivity.this,arrayList);
                gridView.setAdapter(grid_adapter_address);
            }
        });
    }
}