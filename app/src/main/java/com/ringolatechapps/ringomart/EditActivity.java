package com.ringolatechapps.ringomart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;

public class EditActivity extends AppCompatActivity {
    ImageView done,profile_edit;
    FirebaseAuth firebaseAuth;
    private final int PICK_IMAGE_REQUEST = 22;
    EditText first_name,last_name,mobile_num,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        firebaseAuth = FirebaseAuth.getInstance();

        done = findViewById(R.id.done_id);
        profile_edit = findViewById(R.id.imageView_edit_id);
        first_name=findViewById(R.id.first_name_id);
        last_name=findViewById(R.id.last_name_id);
        mobile_num=findViewById(R.id.mob_id);
        email=findViewById(R.id.email_id);

        fill_with_initial_values();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDetails();
                Toast.makeText(EditActivity.this, "updated successfully", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
        profile_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectMainImage();
            }
        });

        setImageIntoImageView();

    }

    private void fill_with_initial_values() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore.collection("USERS").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                first_name.setText(documentSnapshot.get("FirstName").toString());
                last_name.setText(documentSnapshot.get("LastName").toString());
                mobile_num.setText(documentSnapshot.get("PhoneNumber").toString());
                email.setText(documentSnapshot.get("Email").toString());
            }
        });
    }

    private void updateDetails() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("USERS").document(user.getUid()).update("FirstName",first_name.getText().toString().toString());
        firebaseFirestore.collection("USERS").document(user.getUid()).update("LastName",last_name.getText().toString().toString());
        firebaseFirestore.collection("USERS").document(user.getUid()).update("PhoneNumber",mobile_num.getText().toString());
        firebaseFirestore.collection("USERS").document(user.getUid()).update("Email",email.getText().toString());
    }

    private void selectMainImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    private void setImageIntoImageView() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        Glide.with(EditActivity.this).load(firebaseUser.getPhotoUrl()).into(profile_edit);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // Get the Uri of data

            Uri filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profile_edit.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }
}