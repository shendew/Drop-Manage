package com.kingdew.pos3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kingdew.pos3.data.autolog;
import com.skydoves.elasticviews.ElasticImageView;

import io.paperdb.Paper;

public class login extends AppCompatActivity {

    ElasticImageView btn_login;
    TextView createacc,rest,contc;
    EditText lgn_email,lgn_password;
    ProgressBar load;
    CheckBox cb;
    String mail;
    FirebaseAuth auth;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login=findViewById(R.id.lgn_btn);
        lgn_email=findViewById(R.id.lgn_email);
        lgn_password=findViewById(R.id.lgn_pass);
        cb=findViewById(R.id.checkBox);
        createacc=findViewById(R.id.create_acc);
        Paper.init(this);
        contc=findViewById(R.id.contact);
        auth=FirebaseAuth.getInstance();
        load=findViewById(R.id.progressBar2);
        load.setVisibility(View.INVISIBLE);
        rest=findViewById(R.id.reset);


        contc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(login.this,abount.class);
                startActivity(intent);
            }
        });


        rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(login.this,resetpass.class);
                startActivity(intent);
                /*
                FirebaseAuth authe=FirebaseAuth.getInstance();
                Toast.makeText(login.this, "Clicked", Toast.LENGTH_SHORT).show();
                EditText resetmail=new EditText(view.getContext());
                AlertDialog.Builder passrestdialog=new AlertDialog.Builder(view.getContext());
                passrestdialog.setTitle("Password Reset");
                passrestdialog.setMessage("Enter your email to reset your password");
                passrestdialog.setView(resetmail);

                passrestdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            mail=resetmail.getText().toString();
                        }catch (Exception e){
                            resetmail.setError("Enter Email");
                        }

                        authe.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(login.this, "Success", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(login.this, "Failed", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });

                    }
                });
                passrestdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing

                    }
                });
                passrestdialog.create().show();

                */
            }
        });





        createacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(login.this,register.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    load.setVisibility(View.VISIBLE);
                    autolgdata();
                    getdata();

                //Intent intent=new Intent(login.this,home.class);
                //startActivity(intent);

            }
        });
    }

    private void templgn(String catchedmail,String catchedpass) {



        auth.signInWithEmailAndPassword(catchedmail,catchedpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    load.setVisibility(View.INVISIBLE);
                    Intent intent=new Intent(login.this,home.class);
                    startActivity(intent);
                    finish();
                }else {
                    load.setVisibility(View.INVISIBLE);
                    lgn_email.setError("check again");
                    lgn_password.setError("check again");
                }
            }
        });

    }

    private void getdata() {

        String catchedmail =lgn_email.getText().toString();
        String catchedpass =lgn_password.getText().toString();

        if (catchedmail.isEmpty()){
            load.setVisibility(View.INVISIBLE);
            lgn_email.isFocused();

        }
        else if (catchedpass.isEmpty()){
            load.setVisibility(View.INVISIBLE);
            lgn_password.isFocused();
        }else {
            load.setVisibility(View.VISIBLE);
            templgn(catchedmail,catchedpass);
        }


    }


    private void autolgdata(){
        if (cb.isChecked()){

            Paper.book().write(autolog.autologdata,"true");


        }
        else{
            Paper.book().write(autolog.autologdata,"false");
        }

    }
}