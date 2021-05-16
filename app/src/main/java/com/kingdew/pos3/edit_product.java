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

public class edit_product extends AppCompatActivity {
    private DbHandler dbHandler;
    private Context context;
    private Button button;
    private EditText proid,protitle,prolink;
    private String sproid,sprotitle,sprolink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        context=this;
        button=findViewById(R.id.buttonp);
        proid=findViewById(R.id.proID);
        protitle=findViewById(R.id.proTitle);
        prolink=findViewById(R.id.prolink);




        final String id=getIntent().getStringExtra("id");
        dbHandler=new DbHandler(context);
        PRODUCTS products=dbHandler.viewdatapro(Integer.parseInt(id));

        proid.setText(products.getProductnum());
        protitle.setText(products.getPro_title());
        prolink.setText(products.getPro_link());


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                sproid=proid.getText().toString().toLowerCase();
                sprotitle=protitle.getText().toString();
                sprolink=prolink.getText().toString();
                dbHandler=new DbHandler(context);



                if (!sproid.isEmpty()){
                    if (!sprotitle.isEmpty()){
                        if (!sprolink.isEmpty()){


                            PRODUCTS products1= new PRODUCTS(products.getProno(),sproid,sprotitle,sprolink);
                            int stts=dbHandler.updateproduct(products1);
                            Toast.makeText(context, stts+" Updated", Toast.LENGTH_SHORT).show();
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
        });
    }
}