package com.kingdew.pos3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.skydoves.elasticviews.ElasticImageView;

import java.util.List;

public class ap extends AppCompatActivity {

    ListView listViewl;
    ElasticImageView addpro;
    TextView count;
    private List<PRODUCTS> pro;
    PRODUCTS products;
    int counter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ap);

        listViewl=findViewById(R.id.prolist);
        addpro=findViewById(R.id.adpro);
        count=findViewById(R.id.procount);

        context=this;

        DbHandler dbHandler=new DbHandler(this);
        int countpro=dbHandler.countpro();
        count.setText("All Products "+countpro);


        pro=dbHandler.productRows();
        proAdapter prad=new proAdapter(context,R.layout.all_pro_row,pro);

        listViewl.setAdapter(prad);

        addpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(context,add_product.class);
                startActivity(intent);

            }
        });

        listViewl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                products=pro.get(i);
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle(products.getProductnum());
                builder.setMessage(products.getPro_title());

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbHandler.deleteprodata(products.getProno());

                        startActivity(new Intent(context,ap.class));
                        finish();
                    }
                });

                builder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(context,edit_product.class);
                        intent.putExtra("id",String.valueOf(products.getProno()));
                        startActivity(intent);
                        finish();
                    }
                });
                builder.show();




            }
        });
    }
}