package com.kingdew.pos3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skydoves.elasticviews.ElasticImageView;

import java.util.HashMap;
import java.util.regex.Pattern;

public class register extends AppCompatActivity {
    EditText signuname,signmail,signpass,veripass,paynm;
    ElasticImageView signin;
    private String uname,mail,pass,pass2,paynums;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ProgressBar progressBar;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        paynm=findViewById(R.id.pay_num);
        signuname=findViewById(R.id.sign_uname);
        signmail=findViewById(R.id.sign_email);
        signpass=findViewById(R.id.sign_pass);
        signin=findViewById(R.id.sigin_btn);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        veripass=findViewById(R.id.sign_pass2);
        firebaseAuth=FirebaseAuth.getInstance();


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname=signuname.getText().toString();
                mail=signmail.getText().toString();
                pass=signpass.getText().toString();
                pass2=veripass.getText().toString();
                paynums=paynm.getText().toString();

                if (!uname.isEmpty())
                {
                    if (!mail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(mail).matches())
                    {
                        if (!paynums.isEmpty())
                        {
                            if (!pass.isEmpty() && pass.length()>5 && !pass2.isEmpty())
                            {
                                if (pass.equals(pass2)){
                                    registeruser(uname,mail,pass);
                                    progressBar.setVisibility(View.VISIBLE);
                                }else {
                                    signpass.setError("password doesn't match");
                                    veripass.setError("password doesn't match");
                                }

                            }else
                            {
                                signpass.setError("minimum 6 characters");
                            }

                        }else {
                            paynm.setError("required");
                        }



                    }else
                        {
                        signmail.setError("required");
                    }
                }else
                    {
                    signuname.setError("required");
                }


            }
        });

    }

    private void registeruser(String uname, String mail, String pass) {
        firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){


                    user=firebaseAuth.getCurrentUser();
                    String uid=user.getUid();
                    databaseReference= FirebaseDatabase.getInstance().getReference("USERS").child(uid);


                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("id",uid);
                    hashMap.put("user_name",uname);
                    hashMap.put("email",mail);
                    hashMap.put("pay_code",paynums);
                    hashMap.put("image_url","default");

                    databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(register.this, "Successfull Update Data", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(register.this,login.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(register.this, "Error Updating Data "+task.getResult().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });








                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(register.this, "Error Creating User, "+task.getResult().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}