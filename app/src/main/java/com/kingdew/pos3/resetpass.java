package com.kingdew.pos3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetpass extends AppCompatActivity {
    EditText mail;
    Button send;
    FirebaseAuth auth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpass);
        mail=findViewById(R.id.restml);
        send=findViewById(R.id.restmail);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data=mail.getText().toString().trim();


                auth.sendPasswordResetEmail(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       if (task.isSuccessful()){
                           Toast.makeText(resetpass.this, "success", Toast.LENGTH_SHORT).show();
                       }else{
                           Toast.makeText(resetpass.this, "fail", Toast.LENGTH_SHORT).show();
                       }
                    }
                });
            }
        });

    }
}