package com.example.inventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class ProductDetails extends AppCompatActivity {

    ImageView ivBarcode;
    TextView tvDisplayBrandName, tvDisplayProductName, tvDisplayPrice, tvDisplayQty, tvDisplayDesc;
    DatabaseReference reference;
    String id;
    Button btn_generation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        tvDisplayBrandName = findViewById(R.id.tvDisplayBrandName);
        tvDisplayProductName = findViewById(R.id.tvDisplayProductName);
        tvDisplayPrice = findViewById(R.id.tvDisplayPrice);
        tvDisplayQty = findViewById(R.id.tvDisplayQty);
        tvDisplayDesc = findViewById(R.id.tvDisplayDesc);
        ivBarcode = findViewById(R.id.ivBarcode);

        btn_generation = findViewById(R.id.btn_generation);

        btn_generation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barCodeButton(view);
            }
        });
        Intent i = getIntent();
        String ID = i.getStringExtra("ID");
        String brand = i.getStringExtra("brand");
        assert brand != null;
        reference = FirebaseDatabase.getInstance().getReference("brands").child(brand);

        reference.child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvDisplayBrandName.setText(snapshot.child("brand").getValue().toString());
                tvDisplayProductName.setText(snapshot.child("name").getValue().toString());
                tvDisplayPrice.setText(snapshot.child("price").getValue().toString());
                tvDisplayQty.setText(snapshot.child("qty").getValue().toString());
                tvDisplayDesc.setText(snapshot.child("desc").getValue().toString());
                id=snapshot.child("id").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProductDetails.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void barCodeButton(View view){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(id, BarcodeFormat.CODE_128, ivBarcode.getWidth(), ivBarcode.getHeight());
            Bitmap bitmap = Bitmap.createBitmap(ivBarcode.getWidth(), ivBarcode.getHeight(), Bitmap.Config.RGB_565);
            for (int i = 0; i < ivBarcode.getWidth(); i++){
                for (int j = 0; j < ivBarcode.getHeight(); j++){
                    bitmap.setPixel(i,j,bitMatrix.get(i,j)? Color.BLACK:Color.WHITE);
                }
            }
            ivBarcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}