package com.kingdew.pos3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kingdew.pos3.data.autolog;
import com.skydoves.elasticviews.ElasticButton;
import com.skydoves.elasticviews.ElasticImageView;


import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.paperdb.Paper;

public class  home extends AppCompatActivity {
    LinearLayout ap,ao,cs,ab;
    TextView welcome;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    Context context=this;
    ElasticImageView ap1,ao1,cs1,ab1,search,ima;
    FirebaseUser user;
    DatabaseReference databaseReference;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Paper.init(this);
        String lgdata=Paper.book().read(autolog.autologdata);

        if (lgdata.equals("false")){
            auth.signOut();
            Toast.makeText(context, "Sign Out", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //ap=findViewById(R.id.ap);
        welcome=findViewById(R.id.home_name);
        //ao=findViewById(R.id.ao);
        //cs=findViewById(R.id.cs);
        //ab=findViewById(R.id.ab);
        search=findViewById(R.id.searchview);
        Paper.init(this);


        ap1=findViewById(R.id.ap1);
        ao1=findViewById(R.id.ao1);
        cs1=findViewById(R.id.cs1);
        ab1=findViewById(R.id.ab1);
        ima=findViewById(R.id.imageView);

        user=FirebaseAuth.getInstance().getCurrentUser();

        if (user == null){
            Intent intent=new Intent(home.this,login.class);
            startActivity(intent);
            finish();
        }
        String uid=user.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference("USERS").child(uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name=snapshot.child("user_name").getValue().toString();

                welcome.setText("Welcome "+name+" ");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        String getlogdata=Paper.book().read(autolog.autologdata);
        /*if (getlogdata.equals("false")){
            welcome.setText("Welcome, Autolog Disabeld");

        }
        else if (getlogdata.equals("true")){
            welcome.setText("Welcome, Autolog Enabeld");
        }else {
            welcome.setText(getlogdata);
        }*/

        ima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(home.this,User_profile.class);
                startActivity(intent);

            }
        });




        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(home.this,search.class);


                    startActivity(intent);




            }
        });

        ap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(home.this,ap.class);
                startActivity(intent);

            }
        });

        ao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(home.this,ao.class);
                startActivity(intent);
            }
        });
        cs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(home.this,cs.class);
                startActivity(intent);
            }
        });

        ab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(home.this,abount.class);
                startActivity(intent);
            }
        });
    }

    private void logdataset(){

}

}