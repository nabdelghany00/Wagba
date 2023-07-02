package com.example.adminfoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    RadioButton ordered,inprogress,outfordelivery,delivered;
    TextView OrderID;
    Button changeStatus;
    String OID,Status;

    DatabaseReference DataRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ordered=findViewById(R.id.option1);
        inprogress=findViewById(R.id.option2);
        outfordelivery=findViewById(R.id.option3);
        delivered=findViewById(R.id.option4);
        OrderID=findViewById(R.id.edit);
        changeStatus=findViewById(R.id.submit1);
        DataRef = FirebaseDatabase.getInstance().getReference().child("OrderHistory");

        changeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OID=OrderID.getText().toString();

                DataRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot msnap:snapshot.getChildren()){
                            if(msnap.child(OID).exists()){
                                if(ordered.isChecked()){
                                    Toast.makeText(MainActivity.this,"Order Status successfully changed",Toast.LENGTH_SHORT).show();
                                    Status="Ordered";
                                    DataRef.child(msnap.getKey()).child(OID).child("status").setValue(Status);
                                }else if(inprogress.isChecked()){
                                    Toast.makeText(MainActivity.this,"Order Status successfully changed",Toast.LENGTH_SHORT).show();
                                    Status="In Progress";
                                    DataRef.child(msnap.getKey()).child(OID).child("status").setValue(Status);

                                }else if(outfordelivery.isChecked()){
                                    Toast.makeText(MainActivity.this,"Order Status successfully changed",Toast.LENGTH_SHORT).show();
                                    Status="Out for Delivery";
                                    DataRef.child(msnap.getKey()).child(OID).child("status").setValue(Status);


                                }else if(delivered.isChecked()){
                                    Toast.makeText(MainActivity.this,"Order Status successfully changed",Toast.LENGTH_SHORT).show();
                                    Status="Delivered";
                                    DataRef.child(msnap.getKey()).child(OID).child("status").setValue(Status);
                                }else{
                                    Toast.makeText(MainActivity.this,"Please select a status",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
}