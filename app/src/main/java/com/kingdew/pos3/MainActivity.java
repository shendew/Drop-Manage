package com.kingdew.pos3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kingdew.pos3.data.autolog;
import com.kingdew.pos3.data.version_data;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    Button main_log,main_sign;
    TextView ban;
    ProgressBar load;
    FirebaseAuth auth;
    FirebaseUser user;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ban=findViewById(R.id.textView);
        load=findViewById(R.id.load2);
        load.setVisibility(View.VISIBLE);



//process dialog
        ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
//autolog data
        Paper.init(this);
        String data=Paper.book().read(autolog.autologdata);


        progressDialog.startDialog();
//database call
        databaseReference= FirebaseDatabase.getInstance().getReference("VERSION_CONTROL");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String link=snapshot.child("LINK").getValue().toString();
                int code= Integer.valueOf(snapshot.child("VERSION").getValue().toString());
                try {
                    if (code!=version_data.version_code){
                        AlertDialog alertDialog;
                        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Please Update Your App to Continue");
                        builder.setTitle("New Version Found");
                        builder.setNegativeButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                                startActivity(intent);
                            }
                        });
                        builder.setCancelable(false);
                        alertDialog =builder.create();
                        alertDialog.show();
                    }else{
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


                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Database error", Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismissDialog();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });






    }


}