package com.kingdew.pos3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.view.LayoutInflater;

public class ProgressDialog {
    private Activity activity;
    private AlertDialog alertDialog;

    ProgressDialog(Activity mactivity){
        activity=mactivity;
    }
    void startDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater layoutInflater=activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.prodialog,null));
        builder.setCancelable(false);
        alertDialog=builder.create();
        alertDialog.show();
    }
    void dismissDialog(){
        alertDialog.dismiss();
    }
}
