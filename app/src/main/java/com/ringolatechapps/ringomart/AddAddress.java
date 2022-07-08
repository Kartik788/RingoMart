package com.ringolatechapps.ringomart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class AddAddress extends AppCompatActivity {
    TextView use_location,add_address;
    EditText full_name,mob_num,address_1,address_2,landmark,city,state,pincode;
    FirebaseAuth mFirebaseAuth;
    String index,work;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        work = getIntent().getStringExtra("WORK");
        index=getIntent().getStringExtra("POSITION");


        mFirebaseAuth = FirebaseAuth.getInstance();
        use_location = findViewById(R.id.use_current_location_id);
        add_address = findViewById(R.id.add_address_id);

        full_name = findViewById(R.id.full_name_id);
        mob_num = findViewById(R.id.mobile_num_id);
        address_1 = findViewById(R.id.address_1);
        address_2 = findViewById(R.id.address_2);
        landmark = findViewById(R.id.landmark_id);
        city = findViewById(R.id.city_id);
        state = findViewById(R.id.state_id);
        pincode = findViewById(R.id.pincode_id);

        if (work.equals("NEW_ADDRESS")){
            add_address.setText("Add address");
            add_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(all_edittext_filled()){
                        addAddressToFirebaseDatabase();
                    }

                }
            });
        }else{
            add_address.setText("Save changes");
            fillEditText();
            add_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(all_edittext_filled()){
                        editAddressInFirebaseDatabase();
                    }

                }
            });

        }

        use_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchLocationAndPlaceInEditText();
            }
        });




    }

    private boolean all_edittext_filled() {
        if(full_name.getText().toString().replaceAll("\\s+","").isEmpty() || mob_num.getText().toString().replaceAll("\\s+","").isEmpty() || address_1.getText().toString().replaceAll("\\s+","").isEmpty() || address_2.getText().toString().replaceAll("\\s+","").isEmpty() || landmark.getText().toString().replaceAll("\\s+","").isEmpty() || city.getText().toString().replaceAll("\\s+","").isEmpty() || state.getText().toString().replaceAll("\\s+","").isEmpty() || pincode.getText().toString().replaceAll("\\s+","").isEmpty()  ){
            if(full_name.getText().toString().replaceAll("\\s+","").isEmpty())    {
                full_name.setError("required");
            }
            if(mob_num.getText().toString().replaceAll("\\s+","").isEmpty())    {
                mob_num.setError("required");
            }
            if(address_1.getText().toString().replaceAll("\\s+","").isEmpty())    {
                address_1.setError("required");
            }
            if(address_2.getText().toString().replaceAll("\\s+","").isEmpty())    {
                address_2.setError("required");
            }
            if(landmark.getText().toString().replaceAll("\\s+","").isEmpty())    {
                landmark.setError("required");
            }
            if(city.getText().toString().replaceAll("\\s+","").isEmpty())    {
                city.setError("required");
            }
            if(state.getText().toString().replaceAll("\\s+","").isEmpty())    {
                state.setError("required");
            }
            if(pincode.getText().toString().replaceAll("\\s+","").isEmpty())    {
                pincode.setError("required");
            }

            return false;
        }
        return true;
    }

    private void fetchLocationAndPlaceInEditText() {
        // code here


    }


    private void addAddressToFirebaseDatabase() {
        HashMap<String,String> address = new HashMap<>();
        address.put("FullName",full_name.getText().toString());
        address.put("Number",mob_num.getText().toString());
        address.put("AddressLine_1",address_1.getText().toString());
        address.put("AddressLine_2",address_2.getText().toString());
        address.put("Landmark",landmark.getText().toString());
        address.put("City",city.getText().toString());
        address.put("State",state.getText().toString());
        address.put("PinCode",pincode.getText().toString());

        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        String user_id = firebaseUser.getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("USERS").document(user_id).update("Address", FieldValue.arrayUnion(address)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                onBackPressed();
            }
        });
    }
    private void editAddressInFirebaseDatabase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        db.collection("USERS").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ArrayList<HashMap> address_list = (ArrayList<HashMap>)documentSnapshot.get("Address");
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("FullName",full_name.getText().toString());
                hashMap.put("Number",mob_num.getText().toString());
                hashMap.put("AddressLine_1",address_1.getText().toString());
                hashMap.put("AddressLine_2",address_2.getText().toString());
                hashMap.put("Landmark",landmark.getText().toString());
                hashMap.put("City",city.getText().toString());
                hashMap.put("State",state.getText().toString());
                hashMap.put("PinCode",pincode.getText().toString());

                address_list.set(Integer.valueOf(index),hashMap);

                HashMap<String,Object> updated_address = new HashMap<>();
                updated_address.put("Address",address_list);

                db.collection("USERS").document(firebaseUser.getUid()).update(updated_address).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        onBackPressed();
                    }
                });

            }
        });
    }
    private void fillEditText() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        db.collection("USERS").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ArrayList<HashMap> address_list = (ArrayList<HashMap>)documentSnapshot.get("Address");
                HashMap<String,String> address = new HashMap<>();
                address = address_list.get(Integer.valueOf(index));
                full_name.setText(address.get("FullName"));
                mob_num.setText(address.get("Number"));
                address_1.setText(address.get("AddressLine_1"));
                address_2.setText(address.get("AddressLine_2"));
                landmark.setText(address.get("Landmark"));
                city.setText(address.get("City"));
                state.setText(address.get("State"));
                pincode.setText(address.get("PinCode"));
            }
        });
    }


}