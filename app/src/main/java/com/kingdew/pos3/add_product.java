package com.kingdew.pos3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.skydoves.elasticviews.ElasticImageView;

public class add_product extends AppCompatActivity {
    private DbHandler dbHandler;
    private Context context;
    private ElasticImageView button;
    private EditText proid,protitle,prolink;
    private String sproid,sprotitle,sprolink;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(add_product.this,ap.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        context=this;
        button=findViewById(R.id.buttonp);
        proid=findViewById(R.id.proID);
        protitle=findViewById(R.id.proTitle);
        prolink=findViewById(R.id.prolink);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getdata();
            }
        });



    }

    private void getdata() {
        sproid=proid.getText().toString().toLowerCase();
        sprotitle=protitle.getText().toString();
        sprolink=prolink.getText().toString();
        dbHandler=new DbHandler(context);



        if (!sproid.isEmpty()){
            if (!sprotitle.isEmpty()){
                if (!sprolink.isEmpty()){
                    PRODUCTS products= new PRODUCTS(sproid,sprotitle,sprolink);
                    try {
                        dbHandler.addProduct(products);
                        Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }

                    Intent intent=new Intent(context,ap.class);
                    startActivity(intent);
                    finish();
                }else {
                    prolink.setError("Required");
                }
            }else {
                protitle.setError("Required");
            }
        }else {
            proid.setError("Required");
        }





    }
}