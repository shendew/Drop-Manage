package com.kingdew.pos3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class searchAdapter extends ArrayAdapter<ORDERS> {
    private Context context;
    private int resorces;
    List<ORDERS> orders;

    searchAdapter(Context context,int resorces,List<ORDERS> orders){
        super(context,resorces,orders);
        this.context=context;
        this.resorces=resorces;
        this.orders=orders;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View row=layoutInflater.inflate(resorces,parent,false);


        TextView ortv=row.findViewById(R.id.orderidm);
        TextView tit=row.findViewById(R.id.or_title);
        ImageView ntshpd=row.findViewById(R.id.imageView4);
        ImageView shppd=row.findViewById(R.id.imageView3);
        ImageView comptd=row.findViewById(R.id.imageView2);


        ORDERS order=orders.get(position);
        ortv.setText(order.getSorderID());
        tit.setText(order.getSorderTitle());

        if (!order.getScompletedDate().equalsIgnoreCase("null")){
            comptd.setVisibility(View.VISIBLE);

        }else if (!order.getSshippedDate().equalsIgnoreCase("null")){
            comptd.setVisibility(View.INVISIBLE);
            shppd.setVisibility(View.VISIBLE);

        }else if (order.getSshippedDate().equalsIgnoreCase("null") &&order.getSshippedDate().equalsIgnoreCase("null") && order.getScompletedDate().equalsIgnoreCase("null")){
            shppd.setVisibility(View.INVISIBLE);
            ntshpd.setVisibility(View.VISIBLE);
        }


        return row;
    }
}
