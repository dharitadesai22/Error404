package com.example.inventory.ui.AddProduct;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.TextureView;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.widget.TextView;

//import com.google.zxing.integration.android.IntentIntegrator;
//import com.google.zxing.integration.android.IntentResult;
public class AddProductsFragment extends Fragment {

    EditText etProductID, etProductName, etProductPrice, etProductQty, etProductDesc, etBrandName;
    Button btnSaveProduct,bt_scan;
    DatabaseReference reference;
    TextView tv_id;

    public AddProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);

        View view = inflater.inflate(R.layout.fragment_add_products, container, false);
        etProductID = view.findViewById(R.id.etProductID);
        etProductName = view.findViewById(R.id.etProductName);
        etProductPrice = view.findViewById(R.id.etProductPrice);
        etProductQty = view.findViewById(R.id.etProductQty);
        etProductDesc = view.findViewById(R.id.etProductDesc);
        btnSaveProduct = view.findViewById(R.id.btnSaveProduct);
        etBrandName = view.findViewById(R.id.etBrandName);
        reference = FirebaseDatabase.getInstance().getReference("brands");
        bt_scan = view.findViewById(R.id.bt_scan);

        bt_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanButton(view);
            }
        });

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


    //
    public void ScanButton(View view){

        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(AddProductsFragment.this);
        //integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.initiateScan();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null){
            if (intentResult.getContents() == null){
                etProductID.setText("Can not scan!");
            }else {
                Toast.makeText(getActivity(), "id_scan_done", Toast.LENGTH_SHORT).show();
                etProductID.setText(intentResult.getContents());
            }
        }
    }
}