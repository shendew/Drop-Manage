package com.kingdew.pos3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kingdew.pos3.data.autolog;
import com.skydoves.elasticviews.ElasticButton;
import com.skydoves.elasticviews.ElasticImageView;


import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import io.paperdb.Paper;

public class  home extends AppCompatActivity {

    TextView welcome;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    Context context=this;
    ElasticImageView ap1,ao1,cs1,ab1,search,ima;
    FirebaseUser user;
    String uid;

    DatabaseReference databaseReference,databaseReference1;
    String filename;




    @Override
    protected void onDestroy() {
        super.onDestroy();
        Paper.init(this);
        String lgdata=Paper.book().read(autolog.autologdata);

        if (lgdata.equals("false")){
            Toast.makeText(context, "Sign Out", Toast.LENGTH_SHORT).show();
            auth.signOut();

        }
    }
    public void fin(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcome = findViewById(R.id.home_name);
        search = findViewById(R.id.searchview);
        Paper.init(this);


        ap1 = findViewById(R.id.ap1);
        ao1 = findViewById(R.id.ao1);
        cs1 = findViewById(R.id.cs1);
        ab1 = findViewById(R.id.ab1);
        ima = findViewById(R.id.imageView);
        filename = "/data/data/com.kingdew.pos3/databases/OR_MAN";





        try {
            user = FirebaseAuth.getInstance().getCurrentUser();
            uid = user.getUid();
        } catch (Exception e) {
            Intent intent = new Intent(home.this, login.class);
            startActivity(intent);
            finish();
        }

        databaseReference1=FirebaseDatabase.getInstance().getReference("USERS").child(uid);

            try {
                databaseReference = FirebaseDatabase.getInstance().getReference("USERS").child(uid);


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("user_name").getValue().toString();

                        welcome.setText("Welcome " + name + " ");

                        try {
                            String payno = snapshot.child("pay_code").getValue().toString();
                        } catch (Exception e) {
                            Toast.makeText(context, "Please Contact Admin", Toast.LENGTH_SHORT).show();
                            auth.signOut();
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            } catch (Exception e) {
                Toast.makeText(context, "Please Contact Admin", Toast.LENGTH_SHORT).show();
                auth.signOut();
                finish();
            }


            String getlogdata = Paper.book().read(autolog.autologdata);


            ima.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(home.this, User_profile.class);
                    startActivity(intent);
                    finish();

                }
            });


            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(home.this, search.class);


                    startActivity(intent);


                }
            });

            ap1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    try {
                        Intent intent = new Intent(context, ap.class);
                        startActivity(intent);

                    } catch (Exception e) {
                        Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
                    }

                }
            });

            ao1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(home.this, ao.class);
                    startActivity(intent);
                }
            });
            cs1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(home.this, cs.class);
                    startActivity(intent);
                }
            });

            ab1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(home.this, abount.class);
                    startActivity(intent);
                }
            });



    }


}