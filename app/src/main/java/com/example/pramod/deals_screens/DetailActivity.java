package com.example.pramod.deals_screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.firebase.client.Config;
import com.firebase.client.Firebase;
import com.firebase.client.Transaction;

import static java.security.AccessController.getContext;

public class DetailActivity extends AppCompatActivity {

    Firebase ref = null;
    Coupon coupon = null;
     int id = 1001;

    Random ran = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //ImageView imageView = (ImageView) findViewById(R.id.image);
        TextView titleTV = (TextView) findViewById(R.id.title);
        TextView statusTV = (TextView) findViewById(R.id.status);
        TextView locationTV = (TextView) findViewById(R.id.redemptionLocation);
        Button redeemBTN = (Button) findViewById(R.id.redeem);

        Intent intent = getIntent();
        Bundle extras =  intent.getExtras();

        final String passed_title = intent.getExtras().getString("title");
        final String passed_status = intent.getExtras().getString("status");
        final String passed_redemptionlocation = intent.getExtras().getString("redemptionlocation");

        System.out.println("passed_title:" + passed_title);
        System.out.println("passed_status:" + passed_status);
        System.out.println("passed_redemptionlocation:" + passed_redemptionlocation);

        initializeFirebase();

        titleTV.setText("Title: " + passed_title);
        statusTV.setText("Status: " + passed_status);
        locationTV.setText("Redemption Location: " + passed_redemptionlocation);

        redeemBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {

                Log.i("log_tag", "entered onclick method");

                //String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());


                int id = ran.nextInt(2000) + 5;
                String couponID = String.valueOf(id);

                        try{
                            // create a coupon object
                            Log.i("log_tag", "try block");
                            Coupon coupon = new Coupon();
                            coupon.setCouponID(String.valueOf(id));
                            coupon.setDate(date);
                            coupon.setTitle(passed_title);
                            coupon.setStatus(passed_status);
                            coupon.setRedemptionLocation(passed_redemptionlocation);

                            ref = new Firebase(com.example.pramod.deals_screens.DBConfig.FIREBASE_URL);
                            ref.child("Coupons").child(couponID).setValue(coupon); // saving the coupon object in database.

                            System.out.println("id:" +id);
                            id= id+1;
                            System.out.println("id:" +id);

                            Toast.makeText(getApplicationContext(), "Details Saved Successfully", Toast.LENGTH_LONG).show();

                        }catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Data saving failed!!");
                        }
                    }
                });
            }

            private void initializeFirebase() {
                Firebase.setAndroidContext(this);
            }
}
