package com.kingdew.pos3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

public class search extends AppCompatActivity {


    ListView lv;
    EditText data;
    ImageButton button;
    ORDERS orders;
    Context context=this;
    DbHandler dbHandler;
    String gdata;
    LottieAnimationView nodata;
    searchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

            button=findViewById(R.id.serchbtn);
            data=findViewById(R.id.searchbox);
            nodata=findViewById(R.id.nodata);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    viewData();

            }
        });






    }

    private void viewData() {
        LinearLayout view=findViewById(R.id.view);
        view.setVisibility(View.INVISIBLE);
        nodata.setVisibility(View.INVISIBLE);
        TextView id=findViewById(R.id.seorID);
        TextView ti=findViewById(R.id.setitle);


            gdata=data.getText().toString().toLowerCase();

            dbHandler=new DbHandler(context);


            if (gdata.equalsIgnoreCase("")){
                data.setError("Required");
            }else {



                try {


                    try {
                        orders=dbHandler.serach(gdata);

                        if (orders.getSorderID().equalsIgnoreCase(" ")){
                            Toast.makeText(context, "No Data,Check Again", Toast.LENGTH_SHORT).show();
                        }else{
                            nodata.setVisibility(View.INVISIBLE);
                            view.setVisibility(View.VISIBLE);
                            id.setText(orders.getSorderID());
                            ti.setText(orders.getSorderTitle());


                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                                    builder.setTitle(orders.getSorderID());
                                    builder.setMessage(orders.getSorderTitle());
                                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dbHandler.deletesearchor(orders.getSorderID());
                                            finish();

                                        }
                                    });

                                    builder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent=new Intent(context,edit_order.class);
                                            intent.putExtra("id",String.valueOf(orders.getNo()));

                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                    builder.show();
                                }
                            });



                        }

                    }catch (Exception e){
                        PRODUCTS products=dbHandler.search(gdata);
                        if (products.getProductnum().equalsIgnoreCase("")){
                            Toast.makeText(context, "No Data,Check Again", Toast.LENGTH_SHORT).show();
                        }else {
                            nodata.setVisibility(View.INVISIBLE);
                            view.setVisibility(View.VISIBLE);
                            id.setText(products.getProductnum());
                            ti.setText(products.getPro_title());

                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                                    builder.setTitle(products.getProductnum());
                                    builder.setMessage(products.getPro_title());
                                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dbHandler.deletesearchpr(products.getProductnum());
                                            finish();

                                        }
                                    });

                                    builder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent=new Intent(context,edit_product.class);
                                            intent.putExtra("id",String.valueOf(products.getProno()));
                                            finish();
                                            startActivity(intent);
                                        }
                                    });
                                    builder.show();
                                }
                            });
                        }

                    }


                }catch (Exception e){
                    nodata.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "No Data, Try Again Later", Toast.LENGTH_SHORT).show();
                }

            }
    }


}