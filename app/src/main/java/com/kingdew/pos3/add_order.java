
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

public class add_order extends AppCompatActivity {
    private EditText orderID,orderTitle,contactDetails,trackingNumber,shippedDate,completedDate;
    private ElasticImageView addOr;

    private  String sorderID,sorderTitle,scontactDetails,strackingNumber,sshippedDate,scompletedDate;
    private DbHandler dbHandler;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        addOr=findViewById(R.id.button);
        orderID=findViewById(R.id.orderID);
        orderTitle=findViewById(R.id.orderTitle);
        contactDetails=findViewById(R.id.contactDetails);
        trackingNumber=findViewById(R.id.trackingNumber);
        shippedDate=findViewById(R.id.shippedDate);
        completedDate=findViewById(R.id.completedDate);


        addOr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getdata();
            }
        });
    }

    private void getdata() {
        context =this;
        dbHandler=new DbHandler(context);

        sorderID=orderID.getText().toString().toLowerCase();
        sorderTitle=orderTitle.getText().toString();
        scontactDetails=contactDetails.getText().toString();
        strackingNumber=trackingNumber.getText().toString();
        sshippedDate=shippedDate.getText().toString();
        scompletedDate=completedDate.getText().toString();
        // long started=System.currentTimeMillis();

        //check details

        if (!sorderID.isEmpty()){
            if (!sorderTitle.isEmpty()){
                if (!scontactDetails.isEmpty()){


                    if (sshippedDate.isEmpty()){
                        sshippedDate="null";
                    }if (scompletedDate.isEmpty()){
                        scompletedDate="null";
                    }


                    ORDERS orders=new ORDERS(sorderID,sorderTitle,scontactDetails,strackingNumber,sshippedDate,scompletedDate);
                    try {
                        dbHandler.addOrder(orders);
                        Toast.makeText(context, "Order Added", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(context, "task error", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent=new Intent(this,ao.class);

                    startActivity(intent);
                    this.finish();

                }else {
                    contactDetails.setError("Required");
                }

            }else {
                orderTitle.setError("Required");
            }

        }else {
            orderID.setError("Required");
        }







        





    }
}