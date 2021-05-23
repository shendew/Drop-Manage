
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
import com.skydoves.elasticviews.ElasticView;

public class edit_order extends AppCompatActivity {
    EditText orderID,orderTitle,contactDetails,trackingNumber,shippedDate,completedDate;
    ElasticImageView addOr;
    String sorderID,sorderTitle,scontactDetails,strackingNumber,sshippedDate,scompletedDate;
    DbHandler dbHandler;
    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);


        orderID=findViewById(R.id.orderID);
        orderTitle=findViewById(R.id.orderTitle);
        contactDetails=findViewById(R.id.contactDetails);
        trackingNumber=findViewById(R.id.trackingNumber);
        shippedDate=findViewById(R.id.shippedDate);
        completedDate=findViewById(R.id.completedDate);

        addOr=findViewById(R.id.button);






        final String id=getIntent().getStringExtra("id");
        dbHandler=new DbHandler(context);
        ORDERS orders=dbHandler.viewdataor(Integer.parseInt(id));




        orderID.setText(orders.getSorderID());
        orderTitle.setText(orders.getSorderTitle());
        contactDetails.setText(orders.getScontactDetails());
        trackingNumber.setText(orders.getStrackingNumber());
        shippedDate.setText(orders.getSshippedDate());
        completedDate.setText(orders.getScompletedDate());

        addOr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sorderID=orderID.getText().toString().toLowerCase();
                sorderTitle=orderTitle.getText().toString();
                scontactDetails=contactDetails.getText().toString();
                strackingNumber=trackingNumber.getText().toString();
                sshippedDate=shippedDate.getText().toString();
                scompletedDate=completedDate.getText().toString();





                if (!sorderID.isEmpty()){
                    if (!sorderTitle.isEmpty()){
                        if (!scontactDetails.isEmpty()){


                            if (sshippedDate.isEmpty()){
                                sshippedDate="null";
                            }if (scompletedDate.isEmpty()){
                                scompletedDate="null";
                            }


                            ORDERS orders1=new ORDERS(orders.getNo(),sorderID,sorderTitle,scontactDetails,strackingNumber,sshippedDate,scompletedDate);
                            try {
                                int stts=dbHandler.updateorder(orders1);
                                Toast.makeText(context, String.valueOf(stts)+" Updated", Toast.LENGTH_SHORT).show();
                            }catch (Exception e){
                                Toast.makeText(context, "task error", Toast.LENGTH_SHORT).show();
                            }
                            Intent intent=new Intent(context,ao.class);

                            startActivity(intent);
                            finish();

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
        });

    }
}