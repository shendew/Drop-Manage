package com.kingdew.pos3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skydoves.elasticviews.ElasticImageView;

import java.net.URI;
import java.util.HashMap;

public class abount extends AppCompatActivity {
    ElasticImageView terms;
    DatabaseReference databaseReference;
    TextView contactnum1;
    LinearLayout mailcontact,fb;
    String contactnum,fbpage,mail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abount);

        terms=findViewById(R.id.button3);

        mailcontact=findViewById(R.id.mailcntact);
        fb=findViewById(R.id.facebok);
        contactnum1=findViewById(R.id.contactnum);


        databaseReference=FirebaseDatabase.getInstance().getReference("CONTACTINFO");






        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contactnum=snapshot.child("contact_number").getValue().toString();
                 fbpage=snapshot.child("fb").getValue().toString();
                 mail=snapshot.child("mail").getValue().toString();
                 contactnum1.setText("Contact : "+contactnum);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                    fb.setVisibility(View.INVISIBLE);
                    mailcontact.setVisibility(View.INVISIBLE);
                    contactnum1.setText("Data error");
            }
        });




        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(fbpage));
                startActivity(intent);
            }
        });




        mailcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{mail});
                intent.putExtra(Intent.EXTRA_SUBJECT,"Drop Manage App ");
                intent.putExtra(Intent.EXTRA_TEXT,"Type Your Message");
                intent.setType("messsage/rfc822");
                startActivity(Intent.createChooser(intent,"Choose a app : "));
            }
        });


        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}