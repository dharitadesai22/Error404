package com.example.inventory.ui.AddProduct;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.inventory.R;
import com.example.inventory.models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProductsFragment extends Fragment {

    EditText etProductID, etProductName, etProductPrice, etProductQty, etProductDesc, etBrandName;
    Button btnSaveProduct;
    DatabaseReference reference;

    public AddProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_products, container, false);
        etProductID = view.findViewById(R.id.etProductID);
        etProductName = view.findViewById(R.id.etProductName);
        etProductPrice = view.findViewById(R.id.etProductPrice);
        etProductQty = view.findViewById(R.id.etProductQty);
        etProductDesc = view.findViewById(R.id.etProductDesc);
        btnSaveProduct = view.findViewById(R.id.btnSaveProduct);
        etBrandName = view.findViewById(R.id.etBrandName);

        reference = FirebaseDatabase.getInstance().getReference("brands");

        btnSaveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = etProductID.getText().toString();
                String name = etProductName.getText().toString();
                String price = etProductPrice.getText().toString();
                String qty = etProductQty.getText().toString();
                String desc = etProductDesc.getText().toString();
                String brand = etBrandName.getText().toString();

                reference.child(brand).child(ID).setValue(new Product(ID, name, price, qty, desc, brand)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failure\n"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;
    }
}