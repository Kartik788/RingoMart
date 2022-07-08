package com.ringolatechapps.ringomart;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class AcountFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    TextView name, phone;
    ImageView imageview,edit;
    RelativeLayout my_orders,my_wishlist,my_reviews,my_address;
    RelativeLayout account_Settings,sell_on_ringomart,log_out,delete_Acount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_acount, container, false);

        firebaseAuth = FirebaseAuth.getInstance();


        imageview = view.findViewById(R.id.imageView_account);
        edit = view.findViewById(R.id.edit_id);
        name = view.findViewById(R.id.name_id);
        phone = view.findViewById(R.id.phone_id);

        account_Settings = view.findViewById(R.id.settings_rel);
        sell_on_ringomart = view.findViewById(R.id.sell_on_ringomart_rel);
        log_out = view.findViewById(R.id.log_out_rel);
        delete_Acount = view.findViewById(R.id.delete_acount_rel);

        my_orders = view.findViewById(R.id.orders_rel);
        my_wishlist = view.findViewById(R.id.wishlist_rel);
        my_reviews = view.findViewById(R.id.reviews_rel);
        my_address = view.findViewById(R.id.address_rel);

        setName_PhoneNumber();

        my_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_UUID_ordersActivity();
            }
        });

        my_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_UUID_wishlistActivity();
            }
        });

        my_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_UUID_reviewsActivity();
            }
        });

        my_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_UUID_addressActivity();
            }
        });



        sell_on_ringomart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SellOnFlipkartActivity.class));
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),EditActivity.class));
            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                FragmentManager mfragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction mfragmentTransaction = mfragmentManager.beginTransaction();
                mfragmentTransaction.replace(R.id.frame_id, LoginFragment.class, null);
                mfragmentTransaction.commit();
            }
        });

        account_Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),EditActivity.class));
            }
        });

        delete_Acount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "deleted", Toast.LENGTH_SHORT).show();
            }
        });




        return view;
    }

    private void setName_PhoneNumber() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//        name.setText(firebaseUser.getDisplayName());
        Glide.with(getContext()).load(firebaseUser.getPhotoUrl()).into(imageview);
//        phone.setText(firebaseUser.getPhoneNumber().toString());
    }

    private void send_UUID_wishlistActivity() {
        Intent intent = new Intent(getActivity(), WishListActivity.class);
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        intent.putExtra("UUID", firebaseUser.getUid());
        startActivity(intent);
    }

    private void send_UUID_ordersActivity() {
        Intent intent = new Intent(getActivity(), OrdersActivity.class);
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        intent.putExtra("UUID", firebaseUser.getUid());
        startActivity(intent);
    }

    private void send_UUID_addressActivity() {
        Intent intent = new Intent(getActivity(), AddressActivity.class);
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        intent.putExtra("UUID", firebaseUser.getUid());
        startActivity(intent);
    }

    private void send_UUID_reviewsActivity() {
        Intent intent = new Intent(getActivity(), ReviewActivity.class);
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        intent.putExtra("UUID", firebaseUser.getUid());
        startActivity(intent);
    }

}