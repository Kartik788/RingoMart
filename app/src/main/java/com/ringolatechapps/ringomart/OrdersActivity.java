package com.ringolatechapps.ringomart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<PRODUCT_CLASS> arrayList;
    ArrayList<String> product_uuid_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        String UUID = getIntent().getStringExtra("UUID");
        gridView = findViewById(R.id.grid_view_order);

        arrayList = new ArrayList<>();
        product_uuid_list = new ArrayList<>();

        setAllOrders(UUID);
    }

    private void setAllOrders(String uuid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("USERS").document(uuid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> orders_list = (List<String>)documentSnapshot.get("Orders");
                for(String s:orders_list){
                    product_uuid_list.add(s);
                }
                fetch_product_by_uuids();
            }
        });
    }
    private void fetch_product_by_uuids() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        for(String uuid: product_uuid_list){
            db.collection("PRODUCTS").document(uuid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    PRODUCT_CLASS product_class = new PRODUCT_CLASS(uuid,is_null(documentSnapshot),documentSnapshot.get("Name").toString(),documentSnapshot.get("Price").toString(),documentSnapshot.get("Rating").toString());
                    arrayList.add(product_class);

                    if(product_uuid_list.size() == arrayList.size()){
                        GRID_ADAPTER_ORDERS grid_adapter = new GRID_ADAPTER_ORDERS(OrdersActivity.this, arrayList);
                        gridView.setAdapter(grid_adapter);
                    }
                }
            });
        }
    }
    private String is_null(DocumentSnapshot documentSnapshot){
        if(documentSnapshot.get("MainImageUrl") != null){
            return documentSnapshot.get("MainImageUrl").toString();
        }
        return "not_present";
    }
}