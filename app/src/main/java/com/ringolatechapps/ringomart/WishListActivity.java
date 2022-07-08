package com.ringolatechapps.ringomart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class WishListActivity extends AppCompatActivity {

    GridView gridView_wishlist;
    ArrayList<PRODUCT_CLASS> wishlist_product;
    ArrayList<String> wishlist_product_uuids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        gridView_wishlist=findViewById(R.id.grid_view_wishlist);
        String UUID = getIntent().getStringExtra("UUID");
        wishlist_product = new ArrayList<>();
        wishlist_product_uuids = new ArrayList<>();

        setAllWishes(UUID);
        gridView_wishlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in = new Intent(WishListActivity.this, ProductActivity.class);
                in.putExtra("UUID", wishlist_product.get(i).getUUID());
                startActivity(in);
            }
        });
    }

    private void setAllWishes(String uuid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("USERS").document(uuid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> wish_list = (List<String>) documentSnapshot.get("WishList");
                for (String s : wish_list) {
                    wishlist_product_uuids.add(s);
                }
                fetch_product_by_uuids();
            }
        });
    }

    private void fetch_product_by_uuids() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        for (String uuid : wishlist_product_uuids) {
            db.collection("PRODUCTS").document(uuid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    PRODUCT_CLASS product_class = new PRODUCT_CLASS(uuid, is_null(documentSnapshot), documentSnapshot.get("Name").toString(), documentSnapshot.get("Price").toString(), documentSnapshot.get("Rating").toString());
                    wishlist_product.add(product_class);

                    if (wishlist_product_uuids.size() == wishlist_product.size()) {
                        GRID_ADAPTER_WISHLIST grid_adapter = new GRID_ADAPTER_WISHLIST(WishListActivity.this, wishlist_product);
                        gridView_wishlist.setAdapter(grid_adapter);
                    }
                }
            });
        }
    }

    private String is_null(DocumentSnapshot documentSnapshot) {
        if (documentSnapshot.get("MainImageUrl") != null) {
            return documentSnapshot.get("MainImageUrl").toString();
        }
        return "not_present";
    }
}