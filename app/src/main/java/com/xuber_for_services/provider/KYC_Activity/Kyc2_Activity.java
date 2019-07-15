package com.xuber_for_services.provider.KYC_Activity;

import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xuber_for_services.provider.Helper.Sessionwa;
import com.xuber_for_services.provider.R;

import java.io.File;

public class Kyc2_Activity extends AppCompatActivity {

    TextView btn_next2;
    EditText address, city, distict, pin, presentAddress,city1,
            distict1, pin1, contact, email,contact1;

    String  Address, City, Distict, Pin, PresentAddress,
            City1,Distict1, Pin1, Contact, Email;
    String RegNo, Datee, ProName, FatherName, MotherName, Dob, Proof, IdNo,Contact1;
    File destination;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc2_);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        if (Build.VERSION.SDK_INT > 15) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        address = (EditText) findViewById(R.id.address);
        city = (EditText) findViewById(R.id.city);
        distict = (EditText) findViewById(R.id.distict);
        pin = (EditText) findViewById(R.id.pin);
        presentAddress = (EditText) findViewById(R.id.presentAddress);
        city1 = (EditText) findViewById(R.id.city1);
        distict1 = (EditText) findViewById(R.id.distict1);
        pin1 = (EditText) findViewById(R.id.pin1);
        contact = (EditText) findViewById(R.id.contact);
        email = (EditText) findViewById(R.id.email);
        contact1 = (EditText) findViewById(R.id.contact1);
        btn_next2 = (TextView) findViewById(R.id.btn_next2);
        imgBack = (ImageView) findViewById(R.id.imgBack);

        RegNo=getIntent().getStringExtra("RegNo");
        Datee=getIntent().getStringExtra("Datee");
        ProName=getIntent().getStringExtra("ProName");
        FatherName=getIntent().getStringExtra("FatherName");
        MotherName=getIntent().getStringExtra("MotherName");
        Dob=getIntent().getStringExtra("Dob");
        Proof=getIntent().getStringExtra("Proof");
        IdNo=getIntent().getStringExtra("IdNo");
       // destination= new File(getIntent().getStringExtra("destination"));
        destination= (File) getIntent().getSerializableExtra("destination");

        Log.e("img_dest",destination.toString());

        btn_next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Address=address.getText().toString();
                City=city.getText().toString();
                Distict=distict.getText().toString();
                Pin=pin.getText().toString();
                PresentAddress=presentAddress.getText().toString();
                City1=city1.getText().toString();
                Distict1=distict1.getText().toString();
                Pin1=pin1.getText().toString();
                Contact=contact.getText().toString();
                Contact1=contact1.getText().toString();
                Email=email.getText().toString();

                if (!Address.isEmpty()&& !City.isEmpty()&& !Distict.isEmpty()&&
                        !Pin.isEmpty()
                        && !PresentAddress.isEmpty()&& !City1.isEmpty()&& !Distict1.isEmpty()&& !Pin1.isEmpty()&&
                        !Contact.isEmpty()&& !Email.isEmpty()
                        ){

                    Intent intent=new Intent(Kyc2_Activity.this,KycActivity.class);
                    intent.putExtra("RegNo",RegNo);
                    intent.putExtra("Datee",Datee);
                    intent.putExtra("ProName",ProName);
                    intent.putExtra("FatherName",FatherName);
                    intent.putExtra("MotherName",MotherName);
                    intent.putExtra("Dob",Dob);
                    intent.putExtra("Proof",Proof);
                    intent.putExtra("IdNo",IdNo);
                    intent.putExtra("destination",destination);

                    intent.putExtra("Address",Address);
                    intent.putExtra("City",City);
                    intent.putExtra("Distict",Distict);
                    intent.putExtra("Pin",Pin);
                    intent.putExtra("PresentAddress",PresentAddress);
                    intent.putExtra("City1",City1);
                    intent.putExtra("Distict1",Distict1);
                    intent.putExtra("Pin1",Pin1);
                    intent.putExtra("Contact",Contact);
                    intent.putExtra("Contact1",Contact1);
                    intent.putExtra("Email",Email);

                    startActivity(intent);
                } else {
                    displayMessage("Please enter all field");
                    Toast.makeText(Kyc2_Activity.this, "All Field Required", Toast.LENGTH_SHORT).show();
                }


            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Kyc2_Activity.this, Kyc1_Activity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    ///*************************************************************************

    public void displayMessage(String toastString) {
        Log.e("displayMessage", "" + toastString);
        Snackbar snackbar = Snackbar.make(getCurrentFocus(), toastString, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(this.getResources().getColor(R.color.black));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(this.getResources().getColor(R.color.white));
        snackbar.show();
    }
    //*********************************************************************************************************
}
