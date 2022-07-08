package com.ringolatechapps.ringomart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    boolean g=true;
    BottomNavigationView mBottomNavigationView;
    FragmentContainerView mfragmentContainerView;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mfragmentContainerView = findViewById(R.id.frame_id);
        mBottomNavigationView = findViewById(R.id.bottom_navigation_view_id);
        HomePageFragmentTransaction();
        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                FragmentManager mfragmentManager = getSupportFragmentManager();
                FragmentTransaction mfragmentTransaction = mfragmentManager.beginTransaction();


                switch (id) {
                    case R.id.home_id:
                        mfragmentTransaction.replace(R.id.frame_id, HomeFragment.class, null, "home");
                        break;

                    case R.id.categories_id:

                        mfragmentTransaction.replace(R.id.frame_id, CategoriesFragment.class, null);
                        break;

                    case R.id.notifications_id:
                        mfragmentTransaction.replace(R.id.frame_id, NotificationsFragment.class, null);
                        break;

                    case R.id.acount_id:
                        if (is_Logged_In()) {
                            mfragmentTransaction.replace(R.id.frame_id, AcountFragment.class, null);
                        } else {
                            mfragmentTransaction.replace(R.id.frame_id, LoginFragment.class, null);
                        }

                        break;

                    case R.id.cart_id:

                        if (is_Logged_In()) {
                            mfragmentTransaction.replace(R.id.frame_id, CartFragment.class, null);
                        } else {
                            mfragmentTransaction.replace(R.id.frame_id, CartLoginFragment.class, null);
                        }

                        break;
                }
                mfragmentTransaction.commit();
                return true;
            }
        });


    }

    void HomePageFragmentTransaction() {
        FragmentManager mfragmentManager = getSupportFragmentManager();
        FragmentTransaction mfragmentTransaction = mfragmentManager.beginTransaction();
        mfragmentTransaction.replace(R.id.frame_id, HomeFragment.class, null, "home");
        mfragmentTransaction.commit();
    }

    boolean is_Logged_In() {
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();

        if (currentUser != null) {
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {


        HomeFragment myFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("home");
        if (myFragment != null && myFragment.isVisible() && g == false) {
            g=true;
            myFragment.setAllProducts();
        }
        else{
            super.onBackPressed();
        }
    }
}