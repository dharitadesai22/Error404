package com.example.inventory.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventory.DashboardAdmin;
import com.example.inventory.R;
import com.example.inventory.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminSignUp extends AppCompatActivity {

    TextView tv2,tvLogIn;
    EditText etName, etPhoneNum, etEmail, etPassword;
    Button Regbtn;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_up);

        tv2=findViewById(R.id.tv2);
        tvLogIn=findViewById(R.id.tvLogIn);
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etPhoneNum=findViewById(R.id.etPhoneNum);
        etPassword=findViewById(R.id.etPassword);
        Regbtn=findViewById(R.id.Regbtn);
        loading=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference("user");

        Regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name,email,phone,password;
                name=etName.getText().toString();
                email=etEmail.getText().toString();
                phone=etPhoneNum.getText().toString();
                password=etPassword.getText().toString();
                final String key = email.replace(".", "_dot_");
                if(name.length()<4){
                    etName.setError("INVALID");
                }
                if(phone.length()!=10){
                    etPhoneNum.setError("INVALID");
                }
                if(email.length()==0){
                    etEmail.setError("INVALID");
                }
                if(password.length()<7){
                    etPassword.setError("At-least 8 characters required");
                }
                else{
                    loading.setTitle("Create Account");
                    loading.setMessage("Please wait, while we are checking the credentials");
                    loading.setCanceledOnTouchOutside(false);
                    loading.show();
                    reference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.getValue()!=null){
                                Toast.makeText(AdminSignUp.this, "User with given ID card number already exists", Toast.LENGTH_SHORT).show();
                                etEmail.requestFocus();
                            }
                            else{
                                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            User u = new User(name, phone, email, "");
                                            reference.child(key).setValue(u);
                                            Intent i=new Intent(AdminSignUp.this, DashboardAdmin.class);
                                            startActivity(i);
                                            loading.dismiss();
                                            finish();
                                        }
                                        else {
                                            loading.dismiss();
                                            Toast.makeText(AdminSignUp.this, "Account creation failed:(" + "\n" + "Account with provided Email Id already exists"/*task.getException()*/, Toast.LENGTH_LONG).show();
                                            etEmail.requestFocus();
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            loading.dismiss();
                            Toast.makeText(AdminSignUp.this, "Error : "+error, Toast.LENGTH_LONG).show();
                        }
                    });

                }


            }
        });
        tvLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminSignUp.this,AdminLogin.class));
            }
        });

    }
}