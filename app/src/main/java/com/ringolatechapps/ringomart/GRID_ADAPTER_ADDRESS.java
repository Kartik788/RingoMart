package com.ringolatechapps.ringomart;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class GRID_ADAPTER_ADDRESS extends ArrayAdapter<ADDRESS> {


    public GRID_ADAPTER_ADDRESS(Context context, ArrayList<ADDRESS> UUIDarrayList) {
        super(context, 0, UUIDarrayList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.grid_layout_address, parent, false);
        }
        ADDRESS address_obj = getItem(position);  // object that was stored in the arraylist

        TextView name = listitemView.findViewById(R.id.name);
        TextView phone_number = listitemView.findViewById(R.id.phone_number);
        TextView address_1 = listitemView.findViewById(R.id.address_1);
        TextView address_2 = listitemView.findViewById(R.id.address_2);
        TextView landmark = listitemView.findViewById(R.id.landmark);
        TextView city = listitemView.findViewById(R.id.town);
        TextView state = listitemView.findViewById(R.id.state);
        TextView pincode = listitemView.findViewById(R.id.pincode);

        TextView edit = listitemView.findViewById(R.id.edit);
        TextView remove = listitemView.findViewById(R.id.remove);

        name.setText(address_obj.getFullName());
        phone_number.setText(address_obj.getNumber());
        address_1.setText(address_obj.getAddressLine_1());
        address_2.setText(address_obj.getAddressLine_2());
        landmark.setText(address_obj.getLandmark());
        city.setText(address_obj.getCity());
        state.setText(address_obj.getState());
        pincode.setText(address_obj.getPinCode());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getContext(),AddAddress.class);
                in.putExtra("WORK","EDIT_ADDRESS");
                in.putExtra("POSITION",String.valueOf(position));
                getContext().startActivity(in);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAddressFromFirebase(position);
            }
        });


        return listitemView;
    }

    private void removeAddressFromFirebase(int index){
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        db.collection("USERS").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ArrayList<HashMap> address_list = (ArrayList<HashMap>)documentSnapshot.get("Address");

                address_list.remove(index);

                HashMap<String,Object> updated_address = new HashMap<>();
                updated_address.put("Address",address_list);

                db.collection("USERS").document(firebaseUser.getUid()).update(updated_address).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
//                        setAllAddress(firebaseUser.getUid());
                    }
                });

            }
        });
    }
//    private void setAllAddress(String uuid) {
//        ArrayList<ADDRESS> arrayList = new ArrayList<>();
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("USERS").document(uuid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                ArrayList<HashMap> address_list = (ArrayList<HashMap>)documentSnapshot.get("Address");
//                for(HashMap<String,String> m : address_list){
//                    ADDRESS address = new ADDRESS(m.get("AddressLine_1"),m.get("AddressLine_2"),m.get("City"),m.get("FullName"),m.get("Landmark"),m.get("Number"),m.get("Pincode"),m.get("State"));
//                    arrayList.add(address);
//                }
//                GRID_ADAPTER_ADDRESS grid_adapter_address = new GRID_ADAPTER_ADDRESS(AddressActivity.this,arrayList);
//                getContext().gridView.setAdapter(grid_adapter_address);
//            }
//        });
//    }


}
