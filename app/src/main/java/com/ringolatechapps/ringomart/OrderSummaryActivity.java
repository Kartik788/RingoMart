package com.ringolatechapps.ringomart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class OrderSummaryActivity extends AppCompatActivity implements PaymentResultListener {
    TextView continue_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        Checkout.preload(getApplicationContext());
        continue_order = findViewById(R.id.continue_order_summary);
        continue_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });
    }

    public void startPayment() {

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_VEZLxlNgPIkCC0");
        checkout.setImage(R.drawable.logo);

        final Activity activity = this;


        try {

            // currently in test mode so no real payments can be accepted
            JSONObject options = new JSONObject();

            options.put("name", "RingoMart");
            options.put("description", "Grocery at your footsteps");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#9C27B0");
            options.put("currency", "INR");
            options.put("amount", "10000");//pass amount in currency subunits // in cents
            options.put("prefill.email", "kartikringola@gmail.com");
            options.put("prefill.contact", "8433188391");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            Toast.makeText(OrderSummaryActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "success!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();

    }
}