package com.ringolatechapps.ringomart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class SellOnFlipkartActivity extends AppCompatActivity {
    EditText name, price, seller_name;
    Button final_sell, select_image, select_all_image;
    private final int PICK_IMAGE_REQUEST = 22;
    Uri filePath;
    ImageView imageView;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_on_flipkart);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        name = findViewById(R.id.name_id);
        price = findViewById(R.id.price_id);
        seller_name = findViewById(R.id.seller_name);

        final_sell = findViewById(R.id.sell_final_id);
        select_image = findViewById(R.id.select_main_image_btn_id);
        select_all_image = findViewById(R.id.select_all_images_btn_id);


        imageView = findViewById(R.id.img_id);

        final_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadProductOnFirebase();
            }
        });

        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectMainImage();
            }
        });

        select_all_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
    }

    private void uploadProductOnFirebase() {

        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("Name", name.getText().toString());
        hashMap.put("Price", price.getText().toString());
        hashMap.put("Seller", seller_name.getText().toString());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("PRODUCTS").document().set(hashMap);

    }

    private void selectMainImage() {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method
    private void uploadImage() {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
//            StorageReference ref = storageReference.child(UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            storageReference.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast.makeText(SellOnFlipkartActivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast.makeText(SellOnFlipkartActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                                }
                            });
        }
    }
}