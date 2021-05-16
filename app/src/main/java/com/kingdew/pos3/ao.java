package com.kingdew.pos3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;
import androidx.core.view.MenuItemCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.skydoves.elasticviews.ElasticImageView;

import java.util.ArrayList;
import java.util.List;

public class ao extends AppCompatActivity {
    ListView listViewl;
    ElasticImageView addorder;
    TextView count;
    public List<ORDERS> orders;
    ORDERS order;
    List<String> dd;
    int counter;
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ao);
        count=findViewById(R.id.count1);
        addorder=findViewById(R.id.act_ad_order);
        listViewl=findViewById(R.id.listview);
        orders=new ArrayList<>();
        context=this;

        //get counter
        DbHandler dbHandler=new DbHandler(this);
        try {
            counter= dbHandler.countOrder();
        }catch (Exception e){
            Toast.makeText(context, "Error call", Toast.LENGTH_SHORT).show();
        }

        count.setText("All Orders "+counter);

        orders=dbHandler.orderROWS();
        orderAdaper adaper=new orderAdaper(context,R.layout.all_od_row,orders);
        listViewl.setAdapter(adaper);




        listViewl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                 order=orders.get(i);

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle(order.getSorderID());
                builder.setMessage(order.getSorderTitle());
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbHandler.deleteordata(order.getNo());
                        startActivity(new Intent(context,ao.class));
                        finish();
                    }
                });

                builder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(context,edit_order.class);
                        intent.putExtra("id",String.valueOf(order.getNo()));
                        startActivity(intent);
                        finish();

                    }
                });
                builder.show();
            }
        });





        addorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ao.this,add_order.class);
                startActivity(intent);

            }
        });

    }

    /*private void finishbt() {
        LayoutInflater inflater=LayoutInflater.from(context);
        View rw=inflater.inflate(R.layout.all_od_row,null);
        ImageView fini=rw.findViewById(R.id.imageView2);
        ImageView ship=rw.findViewById(R.id.imageView3);
        ImageView notship=rw.findViewById(R.id.imageView4);

        ship.setVisibility(View.INVISIBLE);
        notship.setVisibility(View.INVISIBLE);
        fini.setVisibility(View.VISIBLE);
        finish();
        startActivity(new Intent(context,ao.class));
    }*/


}

