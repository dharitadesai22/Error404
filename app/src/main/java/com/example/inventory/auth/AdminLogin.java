package com.example.inventory.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventory.DashboardAdmin;
import com.example.inventory.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLogin extends AppCompatActivity {

    TextView tv12,tvReg,forgetPass;
    EditText etLoginEmail, etLoginPassword;
    Button btnLogin;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    ProgressDialog loading;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        tv12=findViewById(R.id.tv12);
        tvReg=findViewById(R.id.tvReg);
        forgetPass=findViewById(R.id.forgetpass);
        etLoginEmail=findViewById(R.id.etLoginEmail);
        etLoginPassword=findViewById(R.id.etLoginPassword);
        btnLogin=findViewById(R.id.Loginbtn);
        loading = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("user");

        if (user != null ) {
            startActivity(new Intent(AdminLogin.this, DashboardAdmin.class));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username=etLoginEmail.getText().toString();
                final String password=etLoginPassword.getText().toString();
                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(AdminLogin.this, "Enter credentials", Toast.LENGTH_SHORT).show();
                }
                else{
                    loading.setTitle("LOGGING IN");
                    loading.setMessage("Please wait while we are checking the credentials");
                    loading.setCanceledOnTouchOutside(false);
                    loading.show();
                    firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(AdminLogin.this, DashboardAdmin.class));
                                loading.dismiss();
                                etLoginEmail.setText("");
                                etLoginPassword.setText("");
                                etLoginEmail.requestFocus();
                                finish();
                            }
                            else {
                                Toast.makeText(AdminLogin.this, "Failure \n" + task.getException(), Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminLogin.this, "Failure : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }
                    });

                }

            }
        });
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminLogin.this,ForgetActivity.class));
            }
        });

        tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminLogin.this,AdminSignUp.class));
            }
        });

    }
}