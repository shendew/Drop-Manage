package com.kingdew.pos3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kingdew.pos3.data.autolog;

import android.widget.ProgressBar;
import android.widget.TextView;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    Button main_log,main_sign;
    TextView ban;
    ProgressBar load;
    FirebaseAuth auth;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ban=findViewById(R.id.textView);
        load=findViewById(R.id.load2);

        Paper.init(this);
        load.setVisibility(View.VISIBLE);

        String data=Paper.book().read(autolog.autologdata);

        //auto log
        try {
            user=FirebaseAuth.getInstance().getCurrentUser();

            if (data != null){
                if (data.equals("true") && user!= null){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            load.setVisibility(View.INVISIBLE);

                            Intent intent=new Intent(MainActivity.this,home.class);
                            startActivity(intent);
                            finish();
                        }
                    },3000);
                }else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            load.setVisibility(View.INVISIBLE);

                            Intent intent=new Intent(MainActivity.this,login.class);
                            startActivity(intent);
                            finish();
                        }
                    },3000);
                }
            }
            else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        load.setVisibility(View.INVISIBLE);

                        Intent intent=new Intent(MainActivity.this,login.class);
                        startActivity(intent);
                        finish();
                    }
                },3000);
            }

        }catch (Exception e){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    load.setVisibility(View.INVISIBLE);

                    Intent intent=new Intent(MainActivity.this,login.class);
                    startActivity(intent);
                    finish();
                }
            },3000);

        }

    }
}