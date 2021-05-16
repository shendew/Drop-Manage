package com.kingdew.pos3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kingdew.pos3.data.autolog;
import com.skydoves.elasticviews.ElasticImageView;

import io.paperdb.Paper;

public class login extends AppCompatActivity {

    ElasticImageView btn_login;
    TextView createacc;
    EditText lgn_email,lgn_password;
    ProgressBar load;
    CheckBox cb;
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
        auth=FirebaseAuth.getInstance();
        load=findViewById(R.id.progressBar2);
        load.setVisibility(View.INVISIBLE);





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