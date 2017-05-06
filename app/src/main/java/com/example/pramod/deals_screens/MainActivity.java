package com.example.pramod.deals_screens;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button featured_deals = (Button)findViewById(R.id.featureddeals);
        final Button food_and_drink = (Button)findViewById(R.id.foodanddrink);
        final Button things_to_do = (Button)findViewById(R.id.thingstodo);
        final Button health_and_fitness = (Button)findViewById(R.id.healthandfitness);
        final Button beauty_and_spa = (Button)findViewById(R.id.beautyandspa);


        featured_deals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent featureddealsintent = new Intent(v.getContext(), ShowDeals.class);
                startActivity(featureddealsintent);
            }
        });

        things_to_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent thingstodointent = new Intent(v.getContext(), ShowThingsToDo.class);
                startActivity(thingstodointent);
            }
        });

        food_and_drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent foodanddrinkintent = new Intent(v.getContext(), ShowFoodAndDrink.class);
                startActivity(foodanddrinkintent);
            }
        });

        health_and_fitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent foodanddrinkintent = new Intent(v.getContext(), ShowHealthAndFitness.class);
                startActivity(foodanddrinkintent);
            }
        });

        beauty_and_spa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent foodanddrinkintent = new Intent(v.getContext(), ShowBeautyAndSpa.class);
                startActivity(foodanddrinkintent);
            }
        });
    }


}
