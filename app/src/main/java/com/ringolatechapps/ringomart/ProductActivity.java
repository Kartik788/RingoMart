package com.ringolatechapps.ringomart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductActivity extends AppCompatActivity {

    TextView Name, Price, Highlights, Reviews, Seller;
    TextView add_to_cart,buy_now;
    RatingBar ratingBar;
    FirebaseAuth mFirebaseAuth;
    SliderView sliderView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        mFirebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progress_product_cart);
        sliderView = findViewById(R.id.auto_image_slider_id);
        Name = findViewById(R.id.Name_id);
        ratingBar = findViewById(R.id.Rating_id);
        Price = findViewById(R.id.Price_id);
        Highlights = findViewById(R.id.Highlights_id);
        Reviews = findViewById(R.id.Reviews_id);
        Seller = findViewById(R.id.Seller_id);

        add_to_cart=findViewById(R.id.add_to_cart_id);
        buy_now=findViewById(R.id.buy_now_id);

        String UUID = getIntent().getStringExtra("UUID");

        // NOW YOU HAVE MAIN CONTROL OVER DATABASE BECAUSE YOU HAVE UUID OF THAT PRODUCT
        // USE THIS UUID TO FETCH ANYTHING OF THE PRODUCT
        // UUID

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemToCart(UUID);
            }
        });

        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this,OrderSummaryActivity.class);
                startActivity(intent);
            }
        });

        fetchAndPlaceAllProductInfo(UUID);
    }

    private void addItemToCart(String product_uuid) {
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        String user_id = firebaseUser.getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("USERS").document(user_id).update("Cart", FieldValue.arrayUnion(product_uuid)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ProductActivity.this, "done", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fetchAndPlaceAllProductInfo(String uuid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("PRODUCTS").document(uuid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                progressBar.setVisibility(View.GONE);
                Name.setText(documentSnapshot.get("Name").toString());

                ratingBar.setRating(Float.valueOf(documentSnapshot.get("Rating").toString()));

                Price.setText("₹" + documentSnapshot.get("Price").toString());
                setReviews(documentSnapshot);
                setHighlights(documentSnapshot);

//                if (is_Logged_In()) {
//                    MyAdress.setText("logged in");
//                } else {
//                    MyAdress.setText("not logged in");
//                }

                Seller.setText("Seller - ");
                Seller.append(documentSnapshot.get("Seller").toString());

                setImagesintoSliderView(documentSnapshot);


            }
        });
    }

    private void setImagesintoSliderView(DocumentSnapshot documentSnapshot) {
        if ( documentSnapshot.get("ImageUrl") != null) {
            SliderAdapterExample sliderAdapterExample = new SliderAdapterExample(ProductActivity.this, (List<String>) documentSnapshot.get("ImageUrl"));
            sliderView.setSliderAdapter(sliderAdapterExample);
            sliderView.setIndicatorAnimation(IndicatorAnimationType.SLIDE);
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            sliderView.setAutoCycle(false);
        }

    }

    boolean is_Logged_In() {
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();

        if (currentUser != null) {
            return true;
        }
        return false;
    }

    void setReviews(DocumentSnapshot documentSnapshot) {
        List<HashMap> review_list = (List<HashMap>) documentSnapshot.get("Reviews");
        Reviews.setText("Reviews\n\n");
        if (review_list != null) {
            for (HashMap<String, String> dict : review_list) {
                Map.Entry<String, String> single_key_value = dict.entrySet().iterator().next();
                String heading = single_key_value.getKey();
                String review = single_key_value.getValue();
                Reviews.append("    "+heading + "\n    " + review + "\n\n");

            }
        } else {
            Reviews.setText("no reviews yet");
        }
    }

    void setHighlights(DocumentSnapshot documentSnapshot) {
        List<HashMap> highlights_list = (List<HashMap>) documentSnapshot.get("Highlights");
        Highlights.setText("Highlights\n\n");
        if (highlights_list != null) {
            for (HashMap<String, String> dict : highlights_list) {
                Map.Entry<String, String> single_key_value = dict.entrySet().iterator().next();
                String key = single_key_value.getKey();
                String value = single_key_value.getValue();
                Highlights.append("    "+key + "\n    " + value + "\n\n");

            }
        } else {
            Highlights.setText("no highlights yet");
        }
    }
}