package com.example.inventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductDetails extends AppCompatActivity {

    ImageView ivBarcode;
    TextView tvDisplayBrandName, tvDisplayProductName, tvDisplayPrice, tvDisplayQty, tvDisplayDesc;
    DatabaseReference reference;

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProductDetails.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}