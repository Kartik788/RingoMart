package com.ringolatechapps.ringomart;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {

    GridView listView;
    ArrayList<PRODUCT_CLASS> arrayList;
    ArrayList<String> product_uuid_list;
    float total_price;
    TextView total_price_txt;
    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        listView = view.findViewById(R.id.grid_view_cart);
        progressBar = view.findViewById(R.id.progress_cart_id);
        total_price_txt = view.findViewById(R.id.total_price_id);
        arrayList = new ArrayList<>();
        product_uuid_list = new ArrayList<>();
        fetchUUID_of_Products();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in = new Intent(getContext(), ProductActivity.class);
                in.putExtra("UUID", arrayList.get(i).getUUID());
                startActivity(in);
            }
        });

        return view;
    }

    private void fetchUUID_of_Products() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        String uuid = firebaseUser.getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("USERS").document(uuid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                List<String> list = (List<String>) documentSnapshot.get("Cart");
                for (String s : list) {
                    product_uuid_list.add(s);
                }
                fetch_product_by_uuids();

            }
        });
    }

    private void fetch_product_by_uuids() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        for (String uuid : product_uuid_list) {
            db.collection("PRODUCTS").document(uuid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    PRODUCT_CLASS product_class = new PRODUCT_CLASS(uuid, is_null(documentSnapshot), documentSnapshot.get("Name").toString(), documentSnapshot.get("Price").toString(), documentSnapshot.get("Rating").toString());
                    arrayList.add(product_class);
                    total_price += Float.valueOf(documentSnapshot.get("Price").toString());
//                    if (product_uuid_list.size() == arrayList.size()) {

//                        PRODUCT_CLASS product_class_last = new PRODUCT_CLASS(5,3,67,34,909,232);
//                        arrayList.add(product_class_last);

                    progressBar.setVisibility(View.GONE);
                    total_price_txt.setText("₹" + String.valueOf(total_price));
                    GRID_ADAPTER_CART grid_adapter = new GRID_ADAPTER_CART(getContext(), arrayList);
                    listView.setAdapter(grid_adapter);
//                    }
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