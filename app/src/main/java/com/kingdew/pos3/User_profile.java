package com.kingdew.pos3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class User_profile extends AppCompatActivity {
    TextView uname,umail;
    CircleImageView pro;
    DatabaseReference reference;
    StorageReference upimref;
    FirebaseUser user;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    String uid;
    Button signout;
    private static final int IMAGEBACK=1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        uname=findViewById(R.id.uname);
        umail=findViewById(R.id.uemail);
        pro=findViewById(R.id.profile_image);
        signout=findViewById(R.id.signout);
        user=FirebaseAuth.getInstance().getCurrentUser();
         uid=user.getUid();
        reference= FirebaseDatabase.getInstance().getReference("USERS").child(uid);
        upimref= FirebaseStorage.getInstance().getReference().child("IMAGES");




        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent=new Intent(User_profile.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });





        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name=snapshot.child("user_name").getValue().toString();
                uname.setText(name);
                String mail=snapshot.child("email").getValue().toString();
                umail.setText(mail);
                String pro_link=snapshot.child("image_url").getValue().toString();
                if (!pro_link.equals("default")){
                    Glide.with(User_profile.this).load(pro_link).centerCrop().into(pro);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(User_profile.this, "Error Loading Data", Toast.LENGTH_SHORT).show();

            }
        });

        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,IMAGEBACK);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGEBACK){
            if (resultCode == RESULT_OK){
                Uri imagedata=data.getData();
                final StorageReference imagename=upimref.child(uid+imagedata.getLastPathSegment());


                imagename.putFile(imagedata).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(User_profile.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                        imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                String imageurl=String.valueOf(uri);
                                reference.child("image_url").setValue(imageurl).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(User_profile.this, "url Uploaded", Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }
                        });

                    }
                });

            }
        }

    }
}