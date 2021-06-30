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
    LinearLayout ap,ao,cs,ab;
    TextView welcome;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    Context context=this;
    ElasticImageView ap1,ao1,cs1,ab1,search,ima;
    FirebaseUser user;
    String uid;
    Uri dfile;
    DatabaseReference databaseReference,databaseReference1;
    String filename;
    StorageReference upimref;
    ProgressDialog progressDialog=new ProgressDialog(home.this);


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

        upimref= FirebaseStorage.getInstance().getReference("BACKUPS");
        //Toast.makeText(context, "xxxxxxxxxxx", Toast.LENGTH_SHORT).show();
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //dfile=Uri.parse(snapshot.child("backupUrl").getValue().toString());
                Toast.makeText(context, "valuve listner", Toast.LENGTH_SHORT).show();
                try {
                    if (snapshot.child(uid).child("backupUrl").getValue().toString() == null){
                        //Toast.makeText(context, "correct", Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(context, "success home", Toast.LENGTH_SHORT).show();
                    //StorageReference getfile=upimref.child(uid+"/OR_MAN");
                    //Uri filem= Uri.fromFile(new File(filename));
                }catch (Exception e){
                    //Toast.makeText(context, "catch runned", Toast.LENGTH_SHORT).show();

                    progressDialog.startDialog();
                    uploadDatabase();
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Restore Error", Toast.LENGTH_SHORT).show();
            }
        });








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

    private void uploadDatabase() {
        try {
            Uri filem= Uri.fromFile(new File(filename));

            //Toast.makeText(context, "file get", Toast.LENGTH_SHORT).show();


            upimref.child(uid).child("OR_MAN").putFile(filem).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String data=taskSnapshot.toString();
                   // Toast.makeText(context, "tasksnapshot", Toast.LENGTH_SHORT).show();
                    upimref.child(uid).child("OR_MAN").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //Toast.makeText(context, ""+String.valueOf(uri), Toast.LENGTH_SHORT).show();

                                databaseReference1.child("backupUrl").setValue(String.valueOf(uri)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressDialog.dismissDialog();
                                        //Toast.makeText(context, "all com", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        //Toast.makeText(context, "last fail", Toast.LENGTH_SHORT).show();
                                    }
                                });



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Toast.makeText(context, "link shared failed 2", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Toast.makeText(context, "success"+taskSnapshot.toString(), Toast.LENGTH_SHORT).show();






                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Toast.makeText(context, "failedlistner", Toast.LENGTH_SHORT).show();

                }
            });


        } catch (Exception w) {
            Toast.makeText(context, "" + w.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}