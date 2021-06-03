package com.kingdew.pos3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.internal.service.Common;
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
    TextView uname,umail,precpt;
    CircleImageView pro;
    DatabaseReference reference;
    StorageReference upimref;
    FirebaseUser user;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    String uid;
    Button signout;
   // Switch mode;
    private static final int IMAGEBACK=1;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        uname=findViewById(R.id.uname);
        umail=findViewById(R.id.uemail);
        pro=findViewById(R.id.profile_image);
        signout=findViewById(R.id.signout);
        precpt=findViewById(R.id.prec);
       // mode=findViewById(R.id.mode);
        ProgressDialog progress=new ProgressDialog(User_profile.this);
        progress.startDialog();
        try {
            user=FirebaseAuth.getInstance().getCurrentUser();
            uid=user.getUid();
        }catch (Exception e){
            auth.signOut();
            Toast.makeText(this, "Sigining out,Please wait", Toast.LENGTH_SHORT).show();
            Intent intent=getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();


        }





        try {
            reference= FirebaseDatabase.getInstance().getReference("USERS").child(uid);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    progress.dismissDialog();
                    try {
                        String name=snapshot.child("user_name").getValue().toString();
                        uname.setText(name);
                        try {
                            String pr=snapshot.child("pay_code").getValue().toString();
                            precpt.setText(pr);
                        }catch (Exception e){
                            Toast.makeText(User_profile.this, "Please Contact Admin", Toast.LENGTH_SHORT).show();
                            auth.signOut();
                            home h=new home();
                            h.finish();
                            finish();
                        }


                        String mail=snapshot.child("email").getValue().toString();
                        umail.setText(mail);
                        String pro_link=snapshot.child("image_url").getValue().toString();
                        if (!pro_link.equals("default")){
                            Glide.with(User_profile.this).load(pro_link).centerCrop().into(pro);
                        }
                    }catch (Exception e){
                        uname.setText("Error");
                        umail.setText("Error");
                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(User_profile.this, "Error Loading Data", Toast.LENGTH_SHORT).show();

                }
            });






        }catch (Exception e){
            Toast.makeText(this, "Check Your Connection", Toast.LENGTH_SHORT).show();
            auth.signOut();
            Toast.makeText(this, "Sigining out,Please wait", Toast.LENGTH_SHORT).show();
            Intent intent=getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();


        }

        upimref= FirebaseStorage.getInstance().getReference().child("IMAGES");


/*
        mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Toast.makeText(User_profile.this, "Checked", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(User_profile.this, "Not Checked", Toast.LENGTH_SHORT).show();
                }
            }
        });
*/





        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Toast.makeText(User_profile.this, "Sigining out,Please wait", Toast.LENGTH_SHORT).show();
                Intent intent=getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();


            }
        });

        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                //intent.setType("image/*");


                   // startActivityForResult(intent,IMAGEBACK);

                   // Toast.makeText(User_profile.this, "error", Toast.LENGTH_SHORT).show();


            }
        });

    }
/*
    @Deprecated
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGEBACK){
            if (resultCode == RESULT_OK){
                assert data != null;
                Uri imagedata=data.getData();
                assert imagedata != null;
                final StorageReference imagename=upimref.child(uid+imagedata.getLastPathSegment());


                imagename.putFile(imagedata).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                String imageurl=String.valueOf(uri);
                                reference.child("image_url").setValue(imageurl).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(User_profile.this, "Profile Image Uploaded", Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }

                        });

                    }

                });

            }
        }

    }
    
 */
}