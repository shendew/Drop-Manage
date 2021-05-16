package com.kingdew.pos3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class proAdapter extends ArrayAdapter {

    private Context context;
    private int resorces;
    private List<PRODUCTS> products;


    proAdapter(Context context, int resorces, List<PRODUCTS> products){
        super(context,resorces,products);

        this.context=context;
        this.resorces=resorces;
        this.products=products;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View Row=inflater.inflate(resorces,parent,false);

        TextView no=Row.findViewById(R.id.no);
        TextView ti=Row.findViewById(R.id.prodctti);
        TextView id=Row.findViewById(R.id.proidd);

        PRODUCTS products1=products.get(position);

        int prn=products1.getProno();

        no.setText(" "+prn);
        ti.setText(products1.getPro_title());
        id.setText(products1.getProductnum());





        return Row;
    }
}
